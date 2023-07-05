package ru.sber.project_08.controllers;

import ru.sber.project_08.entities.PaymentInfo;
import ru.sber.project_08.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс отвечающий за обработку запросов об оплате
 */
@Slf4j
@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody PaymentInfo paymentInfo) {
        log.info("Была начата операция платежа");
        boolean isPaymentSuccessful = paymentService.pay(paymentInfo);
        return isPaymentSuccessful ? ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }
}
