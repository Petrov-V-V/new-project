package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.sber.aspects.NotEmptyAspect;

/**
 * Конфигурация
 */
@Configuration
@ComponentScan(basePackages = {"ru.sber.services"})
@EnableAspectJAutoProxy
public class ProjectConfig  {

    @Bean
    public NotEmptyAspect notEmptyAspect() {
        return new NotEmptyAspect();
    }
}