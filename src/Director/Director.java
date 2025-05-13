package Director;
import DataSet.DataSet;

public class Director {
    private String name ;
    private String biography;
    private DataSet[] movies;

    public Director(String name, String biography) {
        this.name = name;
        this.biography = biography;
        this.movies = new DataSet[0];
    }

    public String getName() {
        return this.name;
    }

}