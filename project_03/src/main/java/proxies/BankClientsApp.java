package proxies;

import org.springframework.stereotype.Service;

@Service
public class BankClientsApp {
    public boolean isCustomer(String userId) {
        return (Integer.parseInt(userId) << 2) % 2 == 0 ? true : false;
    }
}
