public class Movie {
    private String name;
    private EnumGenre genre;
    private int releaseYear;
    private Director director;
    private Customer[] customers;
    private boolean isRented;

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
        this.director = new Director(director);
        this.customers = new Customer[0];
        this.isRented = false;
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

    /**
     * <p>Checks if details of one movie equals to the details of the current movie</p>
     * <p><b>Note: 2 movies are the same if they have the same name, release year and director
     * </b></p>
     * @param name name of a movie
     * @param releaseYear release year of a movie
     * @param director director of a movie
     * @return true if the movies are the same, otherwise false
     * */
    public boolean isEquals( String name, int releaseYear, Director director){
        return this.name.equals(name) && this.releaseYear == releaseYear &&
                this.director.isEquals(director);
    }

    public boolean isEquals( Movie movie ){
        return this.name.equals(movie.name) && this.releaseYear == movie.releaseYear &&
                this.director.isEquals(movie.director);
    }
    /**
     * <p>Checks if the movie is rented</p>
     * @return true if the movie is rented, otherwise false
     * */
    public boolean isRented(){
        return this.isRented;
    }

    /**
     * <p>Changes the rent movie status</p>
     * <p><b>Note: if movie is rented, it is changed to be unrented, and if the movie is
     * unrented, it is changed to be rented</b></p>
     * */
    public void changeMovieRentedStatus(){
        this.isRented = !this.isRented;
    }
}
