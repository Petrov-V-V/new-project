package beans;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class Person{
    @Autowired
    @Qualifier("firstParrot")
    private Parrot firstParrot;
    
    @Autowired
    @Qualifier("secondParrot")
    private Parrot secondParrot;

    @Autowired
    private Dog dog;

    @Autowired
    private Cat cat;

    private String name = "Igor";

    @Override
    public String toString() {
        return name + " has: " +  "the first parrot " + firstParrot.getName() +
                ", the second parrot " + secondParrot.getName() +
                ", the dog " + dog.getName() + ", the cat " + cat.getName();
    }

    public Parrot getFirstParrot() {
        return firstParrot;
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
