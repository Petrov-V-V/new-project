package repositories;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public class Database {
    public void writeTransfer(String recipient, BigDecimal amount) {
        System.out.println("Transfer was written into the database");
    }
}
