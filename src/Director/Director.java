package Director;

public class Director {
    private String name ;
    private String biography;

    public Director(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }

    public String getName() {
        return this.name;
    }

}