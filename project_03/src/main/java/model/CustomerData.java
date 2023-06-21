package model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/*
 * Данные клиента
 */
@Component
public class CustomerData {
    private String userId;
    private String phoneNumberToTransfer;
    private BigDecimal amount;

    public CustomerData() {}

    public CustomerData(String userId, String phoneNumberToTransfer, BigDecimal amount) {
        this.userId = userId;
        this.phoneNumberToTransfer = phoneNumberToTransfer;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumberToTransfer() {
        return phoneNumberToTransfer;
  }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhoneNumberToTransfer(String phoneNumberToTransfer) {
        this.phoneNumberToTransfer = phoneNumberToTransfer;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
