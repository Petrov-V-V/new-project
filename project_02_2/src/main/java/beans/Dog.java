package beans;

import org.springframework.stereotype.Component;

@Component
public class Dog{
    private String name = "Fu Han";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog : " + name;
    }
}
