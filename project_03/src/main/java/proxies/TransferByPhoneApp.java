package proxies;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransferByPhoneApp {
    public void transferAmount(String phoneNumber, BigDecimal amount) {
        System.out.println(amount + " was transfered to " + phoneNumber);
    }
}