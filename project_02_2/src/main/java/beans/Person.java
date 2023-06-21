package beans;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

/*
 * Человек
 */
@Component
public class Person{
    private Parrot firstParrot;
    private Parrot secondParrot;
    private Dog dog;
    private Cat cat;

    private String name;

    @Autowired
    public Person(FirstParrot firstParrot, SecondParrot secondParrot, Dog dog, Cat cat) {
        this.firstParrot = firstParrot;
        this.secondParrot = secondParrot;
        this.dog = dog;
        this.cat = cat;
    }

    @Override
    public String toString() {
        return name + " has: " +  "the first parrot " + firstParrot.getName() +
                ", the second parrot " + secondParrot.getName() +
                ", the dog " + dog.getName() + ", the cat " + cat.getName();
    }

    public Parrot getFirstParrot() {
        return firstParrot;
    }

    @PostConstruct
    public void setName() {
        this.name = "Igor";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstParrot(Parrot firstParrot) {
        this.firstParrot = firstParrot;
    }

    public Parrot getSecondParrot() {
        return secondParrot;
    }

    public void setSecondParrot(Parrot secondParrot) {
        this.secondParrot = secondParrot;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
