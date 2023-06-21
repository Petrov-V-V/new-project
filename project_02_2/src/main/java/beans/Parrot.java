package beans;

import org.springframework.stereotype.Component;

/*
 * Попугай
 */
@Component
public interface Parrot{
    public String getName();
    public void setName(String name);
}
