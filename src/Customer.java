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
}


