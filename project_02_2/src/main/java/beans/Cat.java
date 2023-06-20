package beans;

import org.springframework.stereotype.Component;

@Component
public class Cat{
    private String name = "Bay";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat : " + name;
    }
}
