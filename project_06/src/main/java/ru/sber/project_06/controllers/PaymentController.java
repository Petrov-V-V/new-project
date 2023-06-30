package ru.sber.project_06.controllers;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.repositories.PaymentRepository;
import org.springframework.http.ResponseEntity;
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
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<?> pay(@RequestBody PaymentInfo paymentInfo) {
        log.info("Была начата операция платежа");
        boolean isPaymentSuccessful = paymentRepository.pay(paymentInfo);
        return isPaymentSuccessful ? ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }
}
