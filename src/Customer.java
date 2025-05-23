/** Represents a customer with a name and an ID. */
public class Customer {

    /**Customer properties*/
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
        return this.id.equals(customer.getId());
    }

    /**
     * Checks if the specified movie is currently rented by the customer.
     *
     * @param movie the movie to check
     * @return true if the movie is rented by the customer, false otherwise
     */
    public boolean isMovieRented( Movie movie ){
        return this.rentedMovies.isMovieExisting( movie );
    }

    /**
     * Adds a movie to the customer's rented movies collection.
     *
     * @param movie the movie to be added to the rented movies
     */
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


    /**
     * Checks if the customer rented maximum number of movies
     * @return true if the customer rented maximum, false otherwise
     * */
    public boolean isRentedMoviesFull (){
        return rentedMovies.isFull();
    }
}


