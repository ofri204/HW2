/** Represents a customer with a name and an ID. */
public class Customer {

    private String name;
    private String id;
    static final int maxMovies= 5;
    private Customer[] movies;


    /**
     * Constructs a new Costumer with the specified name and ID.
     *
     * @param name the name of the customer
     * @param id the ID of the customer
     */
    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
        this.movies = new Customer[maxMovies];
    }

    /**
     * Returns the name of the customer.
     *
     * @return the customer's name
     */
    public String GetName() {
        return name;
    }

    /**
     * Returns the ID of the customer.
     *
     * @return the customer's ID
     */
    public String GetId(String id) {
        return id;
    }

    /**
     * <p><u>Purpose: Checks if a customer is identical to this customer</u></p>
     * @param customer a customer
     * @return true if the customer are identical, otherwise false*/
    public boolean isEquals( Customer customer ){
        return this.equals( customer );
    }
}


