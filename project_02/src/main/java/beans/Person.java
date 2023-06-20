package beans;

public class Person{
    private String name;
    private Parrot firstParrot;
    private Parrot secondParrot;
    private Dog dog;
    private Cat cat;

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
