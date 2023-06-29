package ru.sber.project_06.services;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.CartProducts;
import ru.sber.project_06.proxy.BankProxy;

/**
 * Класс для совершения процесса оплаты
 */
@Service
public class PaymentService implements PaymentServiceInteface {

    private final JdbcTemplate jdbcTemplate;

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    private BankProxy bankProxy;

    @Autowired
    public PaymentService(BankProxy bankProxy, JdbcTemplate jdbcTemplate) {
        this.bankProxy = bankProxy;
        this.jdbcTemplate = jdbcTemplate;
    }

    // @Override
    // public boolean pay(PaymentInfo paymentInfo) {

    //     String selectSum = "select sum(p.price*pc.count) total from products_petrov_v.clients c join products_petrov_v.products_carts pc on pc.id_cart = c.cart_id join products_petrov_v.products p on p.id = pc.id_product where c.id = ?;";

    //     try (var connection = DriverManager.getConnection(JDBC);
    //        var prepareStatement = connection.prepareStatement(selectSum)){
    //         prepareStatement.setLong(1, paymentInfo.getUserId());
    //         var resultProducts = prepareStatement.executeQuery();
    //         if(resultProducts.next()){
    //             double totalCartPrice = resultProducts.getDouble("total");
    //             if(totalCartPrice != 0){
    //                 return bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice));
    //             }
    //         } else{
    //             throw new RuntimeException("Payment can't be done");
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return false;
    // }

    // @Override
    // @Transactional
    // public boolean pay(PaymentInfo paymentInfo) {
    //     String selectSum = "SELECT SUM(p.price * pc.count) AS total FROM products_petrov_v.clients c JOIN products_petrov_v.products_carts pc ON pc.id_cart = c.cart_id JOIN products_petrov_v.products p ON p.id = pc.id_product WHERE c.id = ?";

    //     PreparedStatementCreator selectStatementCreator = connection -> {
    //         var prepareStatement = connection.prepareStatement(selectSum);
    //         prepareStatement.setLong(1, paymentInfo.getUserId());
    //         return prepareStatement;
    //     };

    //     RowMapper<Double> sumRowMapper = (resultSet, rowNum) -> resultSet.getDouble("total");

    //     List<Double> totals = jdbcTemplate.query(selectStatementCreator, sumRowMapper);

    //     if (!totals.isEmpty()) {
    //         double totalCartPrice = totals.get(0);
    //         if (totalCartPrice != 0) {
    //             return bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice));
    //         }
    //     }

    //     throw new RuntimeException("Payment can't be done");
    // }

    @Override
    @Transactional
    public boolean pay(PaymentInfo paymentInfo) {
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
            if (totalCartPrice != 0) {
                if (bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice))) {
                    List<CartProducts> cartProducts = getCartProducts(paymentInfo.getUserId());
                    
                    for (CartProducts cartProduct : cartProducts) {
                        updateProductCount(cartProduct.getId(), cartProduct.getQuantity());
                    }
                    
                    return true;
                }
            }
        }

        throw new RuntimeException("Payment can't be done");
    }

    private List<CartProducts> getCartProducts(long userId) {
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

    private void updateProductCount(long productId, int quantity) {
        String updateProductCount = "UPDATE products_petrov_v.products SET count = count - ? WHERE id = ?";

        PreparedStatementCreator updateStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateProductCount);
            prepareStatement.setInt(1, quantity);
            prepareStatement.setLong(2, productId);
            return prepareStatement;
        };

        jdbcTemplate.update(updateStatementCreator);
    }

}
