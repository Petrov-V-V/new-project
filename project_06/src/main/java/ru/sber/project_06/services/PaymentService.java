package ru.sber.project_06.services;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Client;
import ru.sber.project_06.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceInteface {

    private ClientRepository clientRepository;

    public PaymentService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean pay(PaymentInfo paymentInfo) {
        Optional<Client> client = clientRepository.findById(paymentInfo.getUserId());
        if (client.isPresent() && client.get().getShoppingCart() != null) {
            BigDecimal totalCartPrice = calculateTotalCartPrice(client.get());
            if (paymentInfo.getSum().compareTo(totalCartPrice) >= 0) {
                client.get().getShoppingCart().getProducts().clear();
                return true;
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
