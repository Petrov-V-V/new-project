package config;

import org.springframework.context.annotation.Configuration;

import beans.Parrot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "beans")
public class ProjectConfig {
    
    @Bean
    @Qualifier("firstParrot")
    public Parrot firstParrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Ying");
        return parrot;
    }

    @Bean
    @Qualifier("secondParrot")
    public Parrot secondParrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Kong Que");
        return parrot;
    }
}