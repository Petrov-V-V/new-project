package proxies;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/*
 * Перевод по номеру телефона
 */
@Component
public class TransferByPhoneApp {
    public void transferAmount(String phoneNumber, BigDecimal amount) {
        System.out.println(amount + " was transfered to " + phoneNumber);
    }
}