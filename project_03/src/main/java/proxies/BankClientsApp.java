package proxies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

/*
 * Проверка на клиента банка
 */
@Component
public class BankClientsApp {
    Set<Integer> setOfUsers = new HashSet<Integer>(Arrays.asList(123432, 123432, 5435143, 134567811));
    
    public boolean isCustomer(String userId) {
        return setOfUsers.contains(Integer.parseInt(userId)) ? true : false;
    }
}
