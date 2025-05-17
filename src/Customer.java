/** Represents a customer with a name and an ID. */
public class Customer {

    private String name;
    private String id;
    private MoviesSet rentedMovies;

    /**
     * Constructs a new Costumer with the specified name and ID.
     *
     * @param name the name of the customer
     * @param id the ID of the customer
     */
    public Customer(String name, String id, int initialMoviesSize, boolean isNumRentMoviesLimited){
        this.name = name;
        this.id = id;
        this.rentedMovies = new MoviesSet(initialMoviesSize, isNumRentMoviesLimited);
    }

    /**
     * Returns the name of the customer.
     *
     * @return the customer's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the ID of the customer.
     *
     * @return the customer's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * <p><u>Purpose: Checks if a customer is identical to this customer</u></p>
     * @param customer a customer
     * @return true if the customer are identical, otherwise false*/
    public boolean isEquals( Customer customer ){
        return this.id.equals(customer.getId()) && this.name.equals(customer.getName()) ;
    }

    public boolean isMovieRented( Movie movie ){
        return this.rentedMovies.isMovieExisting( movie);
    }

    public void addMovie( Movie movie ){
        this.rentedMovies.addNewMovie( movie );
    }

    /**
     * Removes the specified movie from the customer's list of rented movies.
     *
     * @param movie the movie to be removed from the rented movies
     */
    public void removeMovie( Movie movie ){
        this.rentedMovies.removeMovie( movie);
    }

    /**
     * Checks whether the customer has any rented movies.
     *
     * @return true if the customer has no rented movies, false otherwise
     */
    public boolean isRentedMoviesEmpty (){
        return rentedMovies.isEmpty();
    }
}


