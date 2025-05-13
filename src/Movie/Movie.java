package Movie;
import Director.Director;
import EnumGenre.EnumGenre;
import DataSet.DataSet;

public class Movie {
    private String name;
    private EnumGenre genre;
    private int releaseYear;
    private Director director;
    private DataSet[] costumers;


    /**
     * Constructor for the Movie class.
     * Initializes the movie with a name, genre, release year, and director.
     * Also initializes the costumers array as an empty DataSet array.
     *
     * @param name the name of the movie
     * @param genre the genre of the movie
     * @param releaseYear the year the movie was released
     * @param director the director of the movie
     */
    public Movie(String name, EnumGenre genre, int releaseYear, Director director) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.costumers = new DataSet[0];
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
