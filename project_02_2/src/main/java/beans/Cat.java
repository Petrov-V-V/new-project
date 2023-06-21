package beans;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/*
 * Кот
 */
@Component
public class Cat{
    private String name;

    @PostConstruct
    public void setName() {
        this.name = "Bay";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
