package repositories;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * База данных
 */
@Repository
public class Database {
    private ArrayList<ArrayList<Object>> listOfTransfers = new ArrayList<ArrayList<Object>>();

    public void writeTransfer(String recipient, BigDecimal amount) {
        listOfTransfers.add(new ArrayList<Object>(Arrays.asList(recipient, amount)));
        System.out.println("Transfer was written into the database");
    }
}
