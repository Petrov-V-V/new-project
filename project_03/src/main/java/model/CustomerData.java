package model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/*
 * Данные клиента
 */
@Component
public class CustomerData {
    private String userId;
    private String phoneNumber;
    private BigDecimal amount;

    public CustomerData() {}

    public CustomerData(String userId, String phoneNumber, BigDecimal amount) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
  }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
