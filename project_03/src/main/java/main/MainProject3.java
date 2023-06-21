package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.ProjectConfig;
import java.math.BigDecimal;

import model.Customer;
import services.ApplicationService;

public class MainProject3 {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        ApplicationService applicationService = context.getBean(ApplicationService.class);
        Customer customer = context.getBean(Customer.class);
        customer.setUserId("134567811");
        customer.setPhoneNumber("+934567890");
        customer.setAmount(BigDecimal.valueOf(100.0));

        applicationService.verifyUserAndTransfer();

        context.close();
    }
}