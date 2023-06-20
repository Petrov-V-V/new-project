package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;

@Configuration
public class ProjectConfig {
    @Bean
    public Parrot firstParrot(){
        var p = new Parrot();
        p.setName("Ying");
        return p;
    }

    @Bean
    public Parrot secondParrot(){
        var p = new Parrot();
        p.setName("Kong Que");
        return p;
    }

    @Bean
    public Dog dog(){
        var d = new Dog();
        d.setName("Fu Han");
        return d;
    }

    @Bean
    public Cat cat(){
        var c = new Cat();
        c.setName("Bay");
        return c;
    }

    @Bean
    public Person person(){
        var person = new Person();
        person.setFirstParrot(firstParrot());
        person.setSecondParrot(secondParrot());
        person.setDog(dog());
        person.setCat(cat());
        person.setName("Igor");
        return person;
    }
}