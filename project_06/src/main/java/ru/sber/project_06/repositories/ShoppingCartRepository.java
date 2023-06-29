package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.ShoppingCart;
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
    
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    List<ShoppingCart> shoppingCarts = new ArrayList<>(List.of());

    // @Override
    // public Optional<ShoppingCart> addProduct(long cartId, Product product) {
    //     List<Product> products = new ArrayList<>();
    //     var selectSql = "SELECT * from products_petrov_v.products_carts PC join products_petrov_v.products P on PC.id_product = P.id where PC.id_cart = ?";
    //     var insertSql = "insert into products_petrov_v.products_carts(id_product, id_cart, count) values (?,?,?);";
    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareInsertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
    //          var prepareSelectStatement = connection.prepareStatement(selectSql)){
    //         prepareInsertStatement.setLong(1, product.getId());
    //         prepareInsertStatement.setLong(2, cartId);
    //         prepareInsertStatement.setLong(3, product.getQuantity());
    //         prepareInsertStatement.executeUpdate();
    //         prepareSelectStatement.setLong(1, cartId);
    //         var resultProducts = prepareSelectStatement.executeQuery();
    //         while (resultProducts.next()) {
    //             int id = resultProducts.getInt("id");
    //             String name = resultProducts.getString("name");
    //             BigDecimal price = BigDecimal.valueOf(resultProducts.getDouble("price"));
    //             int quantity = resultProducts.getInt("count");
    //             products.add(new Product((long) id, name, price, quantity));
    //         }
    //         return Optional.of(new ShoppingCart((long) cartId, "", products));
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

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


    // @Override
    // public boolean deleteProduct(long cartId, long productId) {
    //     var deleteSql = "DELETE from products_petrov_v.products_carts where id_cart = ? and id_product = ?;";
        
    //     try (var connection = DriverManager.getConnection(JDBC);
    //         var prepareDeleteStatement = connection.prepareStatement(deleteSql);
    //     ) {
    //         prepareDeleteStatement.setLong(1, cartId);
    //         prepareDeleteStatement.setLong(2, productId);
    //         int rowsDeleted = prepareDeleteStatement.executeUpdate();
            
    //         return rowsDeleted > 0;
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

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


    // @Override
    // public Optional<ShoppingCart> updateQuantityOfProduct(long cartId, long productId, int quantity) {
    //     var updateSql = "UPDATE products_petrov_v.products_carts SET count = ? where id_product = ? AND id_cart = ?;";
    //     var selectSql = "SELECT * from products_petrov_v.products_carts PC join products_petrov_v.products P ON PC.id_product = P.id where PC.id_cart = ?";
    //     List<Product> products = new ArrayList<>();
        
    //     try (var connection = DriverManager.getConnection(JDBC);
    //         var prepareUpdateStatement = connection.prepareStatement(updateSql);
    //         var prepareSelectStatement = connection.prepareStatement(selectSql);
    //     ){
    //         prepareUpdateStatement.setInt(1, quantity);
    //         prepareUpdateStatement.setLong(2, productId);
    //         prepareUpdateStatement.setLong(3, cartId);
    //         int rowsUpdated = prepareUpdateStatement.executeUpdate();
            
    //         if (rowsUpdated > 0) {
    //             prepareSelectStatement.setLong(1, cartId);
    //             var resultProducts = prepareSelectStatement.executeQuery();
                
    //             while (resultProducts.next()) {
    //                 int id = resultProducts.getInt("id_product");
    //                 String name = resultProducts.getString("name");
    //                 BigDecimal price = BigDecimal.valueOf(resultProducts.getDouble("price"));
    //                 int productQuantity = resultProducts.getInt("count");
    //                 products.add(new Product((long) id, name, price, productQuantity));
    //             }
                
    //             return Optional.of(new ShoppingCart(cartId, "", products));
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
        
    //     return Optional.empty();
    // }

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



    public ShoppingCart generate(long id){
        ShoppingCart shoppingCart = new ShoppingCart(id, "g45wrewg", new ArrayList<>());
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
    
}