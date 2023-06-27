package ru.sber.project_06.controllers;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс отвечающий за обработку запросов об оплате
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody PaymentInfo paymentInfo) {
        boolean isPaymentSuccessful = paymentService.pay(paymentInfo);
        return isPaymentSuccessful ? ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }
}
