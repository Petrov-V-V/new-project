package beans;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/*
 * Первый попугай
 */
@Component
public class FirstParrot implements Parrot{
    private String name;

    @PostConstruct
    public void setName() {
        this.name = "Ying";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
