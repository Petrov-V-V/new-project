package beans;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/*
 * Второй попугай
 */
@Component
public class SecondParrot implements Parrot{
    private String name;

    @PostConstruct
    public void setName() {
        this.name = "Kong Que";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
