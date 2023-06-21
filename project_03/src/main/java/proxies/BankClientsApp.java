package proxies;

import org.springframework.stereotype.Component;

/*
 * Проверка на клиента банка
 */
@Component
public class BankClientsApp {
    public boolean isCustomer(String userId) {
        return (Integer.parseInt(userId) << 2) % 2 == 0 ? true : false;
    }
}
