public class Movie{

    /**Properties of Movie*/
    private final String name;
    private final Genre genre;
    private final int releaseYear;
    private final Director director;
    private final Utility customersRented;

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
    public Movie(String name, Genre genre, int releaseYear, Director director,
                 int customerAmount) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.customersRented = new Utility(customerAmount, Customer.class );
    }


    /**
     * Returns the release year of the movie.
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

    /**
     * Returns the name of the Movie
     * @return name of the movie
     */
    public String getName(){
        return this.name;
    }

    /** Prints the movie's details, including title, genre, release year, and director **/
    public void printMovie() {
        System.out.println("Title: " + this.name + ", Genre: " + this.genre + ", Year: " + this.releaseYear
                + ", director: " + this.director.getName());
    }

    /**
     * <p>Checks if details of one movie equals to the details of the current movie</p>
     * <p><b>Note: 2 movies are the same if they have the same name, release year and director
     * </b></p>
     * @param movie a movie to compare
     * @return true if the movies are the same, otherwise false
     * */
    @Override
    public boolean equals(Object movie) {
        if( this.isObjKindOf( movie )  ) {
            Movie temp = (Movie) movie;
            return this.name.equals(temp.getName()) && this.releaseYear == temp.getReleaseYear() &&
                    this.director.equals(temp.getDirector());
        }
        return false;
    }


    /**
     * Checks if an object is Movie
     * @param obj object which can be a movie
     * @return true if the object is a movie, false otherwise
     * */
    public final boolean isObjKindOf( Object obj ){
        return Utility.isClassesIdentical( obj.getClass(), this.getClass());
    }

    /**
     * Checks if the movie is rented
     * @return true if the movie is rented, otherwise false
     * */
    public boolean isRented(){
        return !this.customersRented.isArrEmpty();
    }

    /**
     * Adds a new customer to the list of customers who rented movies.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer){
        this.customersRented.addItem( customer );
    }

    /**
     * Removes the specified customer from the list of customers who rented the movie.
     *
     * @param customer the customer to remove
     */
    public void removeCustomer(Customer customer){ this.customersRented.removeItem( customer ); }

}

