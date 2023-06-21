package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.ProjectConfig;
import java.math.BigDecimal;

import model.CustomerData;
import services.ApplicationService;

public class MainProject3 {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        ApplicationService applicationService = context.getBean(ApplicationService.class);
        CustomerData customer = context.getBean(CustomerData.class);
        customer.setUserId("134567811");
        customer.setPhoneNumberToTransfer("+934567890");
        customer.setAmount(BigDecimal.valueOf(100.0));
        applicationService.verifyUserAndTransfer();

        customer.setUserId("218456781");
        customer.setPhoneNumberToTransfer("+934567890");
        customer.setAmount(BigDecimal.valueOf(213));
        applicationService.verifyUserAndTransfer();

        context.close();
    }
}