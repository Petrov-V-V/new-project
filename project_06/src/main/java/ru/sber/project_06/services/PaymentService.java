package ru.sber.project_06.services;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.proxy.BankProxy;
import ru.sber.project_06.entities.Client;
import ru.sber.project_06.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Класс
 */
@Service
public class PaymentService implements PaymentServiceInteface {

    private ClientRepository clientRepository;
    private BankProxy bankProxy;

    public PaymentService(ClientRepository clientRepository, BankProxy bankProxy) {
        this.clientRepository = clientRepository;
        this.bankProxy = bankProxy;
    }

    @Override
    public boolean pay(PaymentInfo paymentInfo) {
        Optional<Client> client = clientRepository.findById(paymentInfo.getUserId());
        if (client.isPresent() && client.get().getShoppingCart() != null) {
            BigDecimal totalCartPrice = calculateTotalCartPrice(client.get());
            if (paymentInfo.getCardNumber() != null) {
                client.get().getShoppingCart().getProducts().clear();
                return bankProxy.checkMeansCustomer(paymentInfo.getCardNumber(), totalCartPrice);
            }
        }
        return false;
    }

    private BigDecimal calculateTotalCartPrice(Client client) {
        return client.getShoppingCart().getProducts().stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
