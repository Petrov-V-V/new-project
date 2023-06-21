package services;

import proxies.TransferByPhoneApp;
import proxies.BankClientsApp;
import repositories.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.Customer;

@Service
public class ApplicationService {
    private Customer customer;
    private BankClientsApp bankClientsApp;
    private TransferByPhoneApp transferByPhoneApp;
    private Database database;

    @Autowired
    public ApplicationService(BankClientsApp bankClientsApp, TransferByPhoneApp transferByPhoneApp, Database database, Customer customer) {
        this.bankClientsApp = bankClientsApp;
        this.transferByPhoneApp = transferByPhoneApp;
        this.database = database;
        this.customer = customer;
    }

    public void verifyUserAndTransfer() {
        boolean isCustomer = bankClientsApp.isCustomer(customer.getUserId());
        if (isCustomer) {
            transferByPhoneApp.transferAmount(customer.getPhoneNumber(), customer.getAmount());
            database.writeTransfer(customer.getUserId(), customer.getAmount());
        } else {
            System.out.println("Error: User is not a bank customer.");
        }
    }
}
