package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.ProjectConfig;
import beans.Person;

public class MainProject2 {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var bean = context.getBean(Person.class);   
        System.out.println(bean);
        context.close();
    }
}