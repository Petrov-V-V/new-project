package ru.sber.project_06.proxy;

import ru.sber.project_06.entities.BankAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Класс для совершения операций банка
 */
@Component
public class BankProxy implements BankProxyInterface {
    List<BankAccount> accountList = List.of(new BankAccount("2135434165", BigDecimal.valueOf(3333)));

    @Override
    public boolean checkMeansCustomer(String numberOfCard, BigDecimal sum) {
        Optional<BankAccount> account = accountList.stream().filter(bankAccount -> bankAccount.getCardNumber().equals(numberOfCard)).findFirst();
        if (account.get().getSum().compareTo(sum) == 1) {
            return true;
        }
        return false;
    }
}