package ru.sber.project_06.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.CartProducts;
import ru.sber.project_06.proxy.BankProxy;
import ru.sber.project_06.repositories.ProductRepository;
import ru.sber.project_06.repositories.ShoppingCartRepository;

/**
 * Класс для совершения процесса оплаты
 */
@Service
public class PaymentService implements PaymentServiceInteface {

    private BankProxy bankProxy;
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;

    public PaymentService(BankProxy bankProxy, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.bankProxy = bankProxy;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public boolean pay(PaymentInfo paymentInfo) {
        var totalCartPrice = shoppingCartRepository.calcTotalSum(paymentInfo);
        if (totalCartPrice != 0) {
            if (bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice))) {
                List<CartProducts> cartProducts = shoppingCartRepository.getCartProducts(paymentInfo.getUserId());
                
                for (CartProducts cartProduct : cartProducts) {
                    if (cartProduct.getQuantity() <= cartProduct.getCount()){
                    productRepository.updateProductCount(cartProduct.getId(), cartProduct.getQuantity());
                } else {
                    throw new RuntimeException("Payment can't be done due to lack of products");
                }
                }
                
                return true;
            }
        }

        throw new RuntimeException("Payment can't be done due to insufficient funds in the bank account");
    }

}
