package services;

import proxies.TransferByPhoneApp;
import proxies.BankClientsApp;
import repositories.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.CustomerData;

@Service
public class ApplicationService {
    private CustomerData customerData;
    private BankClientsApp bankClientsApp;
    private TransferByPhoneApp transferByPhoneApp;
    private Database database;

    @Autowired
    public ApplicationService(BankClientsApp bankClientsApp, TransferByPhoneApp transferByPhoneApp, Database database, CustomerData customerData) {
        this.bankClientsApp = bankClientsApp;
        this.transferByPhoneApp = transferByPhoneApp;
        this.database = database;
        this.customerData = customerData;
    }

    public void verifyUserAndTransfer() {
        boolean isCustomer = bankClientsApp.isCustomer(customerData.getUserId());
        if (isCustomer) {
            transferByPhoneApp.transferAmount(customerData.getPhoneNumber(), customerData.getAmount());
            database.writeTransfer(customerData.getUserId(), customerData.getAmount());
        } else {
            System.out.println("Error: User is not a bank customer.");
        }
    }
}
