package Movie;
import Director.Director;
import EnumGenre.EnumGenre;

public class Movie {
    private String name;
    private EnumGenre genre;
    private int releaseYear;
    private Director director;


    public Movie(String name, EnumGenre genre, int releaseYear, Director director) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public String getDirectorName() {
        return this.director.getName();
    }

    public void printMovie() {
        System.out.println("Title:" + this.name + ", Genre:" + this.genre + ", Year:" + this.releaseYear
                + ", Director:" + getDirectorName());
    }
}
