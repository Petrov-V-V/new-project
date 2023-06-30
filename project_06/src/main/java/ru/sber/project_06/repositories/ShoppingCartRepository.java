package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.entities.CartProducts;
import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Класс извлекающий данные о тележке из бд для дальнейшей их обработки
 */
@Repository
public class ShoppingCartRepository implements ShoppingCartRepositoryInteface {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoppingCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ShoppingCart> addProduct(long cartId, Product product) {
        List<Product> products = new ArrayList<>();
        var selectSql = "SELECT * FROM products_petrov_v.products_carts PC JOIN products_petrov_v.products P ON PC.id_product = P.id WHERE PC.id_cart = ?";
        var insertSql = "INSERT INTO products_petrov_v.products_carts (id_product, id_cart, count) VALUES (?, ?, ?)";

        PreparedStatementCreator insertStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setLong(1, product.getId());
            prepareStatement.setLong(2, cartId);
            prepareStatement.setLong(3, product.getQuantity());
            return prepareStatement;
        };

        PreparedStatementCreator selectStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setLong(1, cartId);
            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id_product");
            String name = resultSet.getString("name");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            int quantity = resultSet.getInt("count");
            return new Product((long) id, name, price, quantity);
        };

        jdbcTemplate.update(insertStatementCreator);
        products = jdbcTemplate.query(selectStatementCreator, productRowMapper);

        return Optional.of(new ShoppingCart((long) cartId, "", products));
    }

    @Override
    public boolean deleteProduct(long cartId, long productId) {
        var deleteSql = "DELETE FROM products_petrov_v.products_carts WHERE id_cart = ? AND id_product = ?";
        
        PreparedStatementCreator deleteStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, cartId);
            prepareStatement.setLong(2, productId);
            return prepareStatement;
        };
        
        int rowsDeleted = jdbcTemplate.update(deleteStatementCreator);
        
        return rowsDeleted > 0;
    }

    @Override
    public Optional<ShoppingCart> updateQuantityOfProduct(long cartId, long productId, int quantity) {
        var updateSql = "UPDATE products_petrov_v.products_carts SET count = ? WHERE id_product = ? AND id_cart = ?";
        var selectSql = "SELECT * FROM products_petrov_v.products_carts PC JOIN products_petrov_v.products P ON PC.id_product = P.id WHERE PC.id_cart = ?";
        List<Product> products = new ArrayList<>();

        PreparedStatementCreator updateStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setInt(1, quantity);
            prepareStatement.setLong(2, productId);
            prepareStatement.setLong(3, cartId);
            return prepareStatement;
        };

        PreparedStatementCreator selectStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setLong(1, cartId);
            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id_product");
            String name = resultSet.getString("name");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            int productQuantity = resultSet.getInt("count");
            return new Product((long) id, name, price, productQuantity);
        };

        int rowsUpdated = jdbcTemplate.update(updateStatementCreator);

        if (rowsUpdated > 0) {
            products = jdbcTemplate.query(selectStatementCreator, productRowMapper);
            return Optional.of(new ShoppingCart(cartId, "", products));
        }

        return Optional.empty();
    }

    @Override
    public double calcTotalSum(PaymentInfo paymentInfo) {
        String selectSum = "SELECT SUM(p.price * pc.count) AS total FROM products_petrov_v.clients c JOIN products_petrov_v.products_carts pc ON pc.id_cart = c.cart_id JOIN products_petrov_v.products p ON p.id = pc.id_product WHERE c.id = ?";

        PreparedStatementCreator selectStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSum);
            prepareStatement.setLong(1, paymentInfo.getUserId());
            return prepareStatement;
        };

        RowMapper<Double> sumRowMapper = (resultSet, rowNum) -> resultSet.getDouble("total");

        List<Double> totals = jdbcTemplate.query(selectStatementCreator, sumRowMapper);

        if (!totals.isEmpty()) {
            double totalCartPrice = totals.get(0);
            return totalCartPrice;
        }

        throw new RuntimeException("Payment can't be done due to empty cart");
    }

    @Override
    public List<CartProducts> getCartProducts(long userId) {
        String selectCartProducts = "SELECT p.id, p.count, pc.count AS quantity FROM products_petrov_v.products p JOIN products_petrov_v.products_carts pc ON pc.id_product = p.id JOIN products_petrov_v.clients c ON c.cart_id = pc.id_cart WHERE c.id = ?";

        PreparedStatementCreator selectStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectCartProducts);
            prepareStatement.setLong(1, userId);
            return prepareStatement;
        };

        RowMapper<CartProducts> productRowMapper = (resultSet, rowNum) -> {
            long id = resultSet.getLong("id");
            int count = resultSet.getInt("count");
            int quantity = resultSet.getInt("quantity");
            return new CartProducts(id, count, quantity);
        };

        return jdbcTemplate.query(selectStatementCreator, productRowMapper);
    }
    
}