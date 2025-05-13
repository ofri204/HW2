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


    /**
     * Returns the release year of the movie.
     *
     * @return the year the movie was released
     */
    public int getReleaseYear() {
        return this.releaseYear;
    }

    /**
     * Returns the name of the movie's director.
     *
     * @return the director's name
     */
    public String getDirectorName() {
        return this.director.getName();
    }


    /** Prints the movie's details, including title, genre, release year, and director **/
    public void printMovie() {
        System.out.println("Title:" + this.name + ", Genre:" + this.genre + ", Year:" + this.releaseYear
                + ", Director:" + getDirectorName());
    }
}
