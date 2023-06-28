package ru.sber.project_06.services;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.proxy.BankProxy;

/**
 * Класс для совершения процесса оплаты
 */
@Service
public class PaymentService implements PaymentServiceInteface {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    private BankProxy bankProxy;

    public PaymentService(BankProxy bankProxy) {
        this.bankProxy = bankProxy;
    }

    @Override
    public boolean pay(PaymentInfo paymentInfo) {

        String selectSum = "select sum(p.price*pc.count) total from petrov.client c join petrov.product_client pc on pc.id_cart = c.cart_id join petrov.product p on p.id = pc.id_product where c.id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
           var prepareStatement = connection.prepareStatement(selectSum)){
            prepareStatement.setLong(1, paymentInfo.getUserId());
            var resultProducts = prepareStatement.executeQuery();
            if(resultProducts.next()){
                double totalCartPrice = resultProducts.getDouble("total");
                if(totalCartPrice != 0){
                    return bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice));
                }
            } else{
                throw new RuntimeException("Payment can't be done");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
