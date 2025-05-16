public class Movie {
    private String name;
    private EnumGenre genre;
    private int releaseYear;
    private Director director;
    private CustomerSet customersRented;



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
    public Movie(String name, EnumGenre genre, int releaseYear, Director director,
                 int defaultCustomerSize, boolean isCustomersRentedLimited) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.customersRented = new CustomerSet(defaultCustomerSize, isCustomersRentedLimited);

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
    public Director getDirector() {
        return this.director;
    }

    public String getName(){
        return this.name;
    }

    /** Prints the movie's details, including title, genre, release year, and director **/
    public void printMovie() {
        System.out.println("Title:" + this.name + ", Genre:" + this.genre + ", Year:" + this.releaseYear
                + ", Director:" + this.director.getName());
    }

    /**
     * <p>Checks if details of one movie equals to the details of the current movie</p>
     * <p><b>Note: 2 movies are the same if they have the same name, release year and director
     * </b></p>
     * @param movie a movie to compare
     * @return true if the movies are the same, otherwise false
     * */
    public boolean isEquals( Movie movie){
        return this.name.equals( movie.getName() ) && this.releaseYear == movie.getReleaseYear() &&
                this.director.isEquals(movie.getDirector() );
    }

    /**
     * <p>Checks if the movie is rented</p>
     * @return true if the movie is rented, otherwise false
     * */
    public boolean isRented(){
        return !customersRented.isEmpty();
    }

}

