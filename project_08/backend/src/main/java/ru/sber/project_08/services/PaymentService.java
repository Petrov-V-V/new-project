package ru.sber.project_08.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import ru.sber.project_08.entities.PaymentInfo;
import ru.sber.project_08.entities.CartProducts;
import ru.sber.project_08.proxy.BankProxy;
import ru.sber.project_08.entities.Cart;
import ru.sber.project_08.repositories.CartRepository;
import ru.sber.project_08.repositories.ProductCartRepository;

/**
 * Класс для совершения процесса оплаты
 */
@Service
public class PaymentService implements PaymentServiceInteface {

    private final ProductCartRepository productCartRepository;
    private BankProxy bankProxy;
    private CartService cartService;
    private ProductService productService;
    private CartRepository cartRepository;

    @Autowired
    public PaymentService(CartRepository cartRepository, BankProxy bankProxy, CartService cartService, ProductService productService, ProductCartRepository productCartRepository) {
        this.bankProxy = bankProxy;
        this.cartService = cartService;
        this.productService = productService;
        this.productCartRepository = productCartRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public boolean pay(PaymentInfo paymentInfo) {
        var totalCartPrice = cartService.calcTotalSum(paymentInfo);
        if (totalCartPrice != 0) {
            if (bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), BigDecimal.valueOf(totalCartPrice))) {
                List<CartProducts> cartProducts = cartService.getCartProducts(paymentInfo.getUserId());
                
                    System.out.println(cartProducts);
                for (CartProducts cartProduct : cartProducts) {
                if (cartProduct.getQuantity() <= cartProduct.getCount()){
                    productService.updateProductCount(cartProduct.getId(), cartProduct.getQuantity());
                } else {
                    throw new RuntimeException("Payment can't be done due to lack of products");
                }
                }
                deleteProductCartsByClient(cartRepository.findById(paymentInfo.getUserId()).get());
                return true;
            } else {
                    throw new RuntimeException("Payment can't be done due to lack of products");
            }
        }

        throw new RuntimeException("Payment can't be done due to insufficient funds in the bank account");
    }

    public void deleteProductCartsByClient(Cart cart) {
        productCartRepository.deleteByCart(cart);
    }

}
