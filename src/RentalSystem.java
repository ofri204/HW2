public class RentalSystem {

    /**<p><u>General System Data Sets</u></p>*/
    private CustomerSet customers;
    private MoviesSet movies;
    private DirectorSet directors;


    /**<p><u>General Properties of Data Sets</u></p>*/
    private static final int maxMovieNum = 30;
    private static final int maxCustomerNum = 30;
    private static final boolean isMovieSetFinal = true;
    private static final boolean isCustomerSetFinal = true;
    private static final boolean isDirectorSetFinal = false;
    private static final boolean isCustomerMovieSetFinal = true;


    /** <p><u>General System Messages</u></p>*/
    private static final String customerReachedLimitMessage = "The customer has reached the limit";
    private static final String customerAlreadyRentedMessage = "Customer already has this movie";
    private static final String customerNotFoundMessage = "Customer not found.";
    private static final String customerCantReturnMessage = "Customer cannot return the movie.";
    private static final String movieInSystemMessage = "Movie is already in the system.";
    private static final String noRentedMoviesMessage = "No Rented movies.";
    private static final String movieIsnNotExistMessage = "No such movie exists.";
    private static final String cannotRemoveMovieMessage = "Cannot remove rented movie.";
    private static final String cannotAddMovieMessage = "Cannot add movie.";
    private static final String movieAddedToSystemMessage = "Movie added to system successfully";
    private static final String cannotFindRentedMovies = "No Rented movies";
    private static final String cannotFindUnRentedMovies = "No Unrented movies";
    private static final String rentedMoviesArrMessage = "Rented Movies:";
    private static final String unRentedMoviesArrMessage = "Unrented Movies:";
    private static final String systemIsFullMessage= " System is full, Cannot add more movies.";

    /**<p><u>RentalSystem Class Builder</u></p>*/
    public RentalSystem(){
        this.customers = new CustomerSet(maxCustomerNum, isCustomerSetFinal);
        this.directors = new DirectorSet(maxMovieNum, isDirectorSetFinal);
        this.movies = new MoviesSet(maxMovieNum, isMovieSetFinal);
    }

    /**<u>Purpose: Rent an existing movie for a customer</u>
     * <p><b><br>Notes:
     * <br>- Movie must exist in system
     * <br>- Customer array must not be full if the customer is new
     * <br>- Customer cannot rent the same movie twice</b></p>
     * <b>Used functions: {@link #hasRentMovieErrors(Movie, Customer)}, {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customerName Customer's name
     * @param customerId Customer's ID
     * @param movieName Movie title
     * @param releaseYear Release year of the movie
     * @param directorName Director's name
     */
    public void rentMovie(String customerName, String customerId, String movieName, int releaseYear,
            String directorName) {
        Customer tempCustomer = new Customer(customerName, customerId,
                RentalSystem.maxCustomerNum, RentalSystem.isCustomerMovieSetFinal);

        Director tempDirector = new Director(
                directorName, null, 0, false);

        Movie tempMovie = new Movie(movieName, null, releaseYear, tempDirector, 0, false);

        //check for errors
        if ( hasRentMovieErrors( tempMovie, tempCustomer) ) {
            return;
        }

        //add the movie to user, and the user to movie
        updateMovieAndCustomer( tempMovie, tempCustomer);
    }


    /**<u>Purpose: Remove a movie from the system if it's not currently rented</u>
     * <br><b>Used functions: {@link #hasRemoveMovieErrors(Movie)}, {@link #findMovie(Movie)}, {@link #findDirector(Movie)}, {@link #removeDirector(Director)}</b>
     * @param movieName Movie title
     * @param releaseYear Release year
     * @param directorName Director's name
     */
    public void removeMovie(String movieName, int releaseYear, String directorName){
        Director tempDirector = new Director( directorName, null, 0, false );
        Movie tempMovie = new Movie( movieName, null ,releaseYear, tempDirector, 0, false);

        //check errors
        if( this.hasRemoveMovieErrors( tempMovie) ){
            return;
        }

        //remove the movie from movies
        Movie realMovie = this.findMovie( tempMovie );
        this.movies.removeMovie(realMovie);

        Director movieDirector = this.findDirector( realMovie );
        movieDirector.removeMovie( realMovie ); //remove movie from director

        this.removeDirector( movieDirector ); //remove director if he hasn't any movies
    }

    /**<u>Purpose: Remove a director if they have no more movies associated</u>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param director {@code director} to check and potentially remove
     */
    private void removeDirector( Director director ){
        if( !director.hasMovies() ){
            this.directors.removeDirector( director );
        }
    }

    /**
     * Finds an existing director or adds a new one if not already in the system.
     * A helper method for {@code AddMovie} function
     *
     * @param directorName the name of the director
     * @param biography the biography of the director
     * @return the existing or newly added director
     */
    private Director findOrAddDirector(String directorName , String biography){
        Director tempDirector = new Director(directorName, biography, maxMovieNum, isMovieSetFinal);
        if (!(directors.isDirectorExisting(tempDirector))) {
            directors.addNewDirector(tempDirector);
        }
        return directors.findDirector(tempDirector);
    }

    /**
     * Checks whether the given director is valid (not null)
     * If the director is null, prints an error message
     * A helper method for {@code AddMovie} function
     *
     * @param director the Director object
     * @return true if the director is valid, false otherwise
     */
    private boolean IsDirectorValid (Director director){
        if (director == null) {
            printMessage(cannotAddMovieMessage);
            return false;
        }
        return true;
    }


    private boolean isMoviesFull() {
        return movies.getActiveMovies() >= maxMovieNum;
    }

    /**
     * Adds a new movie to the system. If the director does not exist, adds the director as well.
     * Prints a success message if the movie was added, or an error if the director is invalid.
     *
     * @param movieName the name of the movie
     * @param genre the genre of the movie
     * @param releaseYear the year the movie was released
     * @param directorName the name of the movie's director
     * @param biography the biography of the director
     */
    public void addMovie(String movieName, Genre genre, int releaseYear, String directorName, String biography) {
      Director director = findOrAddDirector(directorName, biography);
        if (!IsDirectorValid(director)) {
            return;
        }

        if (isMoviesFull()) {
            printMessage(systemIsFullMessage);
            return;
        }

        Movie newMovie = new Movie(movieName, genre, releaseYear, director, maxCustomerNum, isCustomerSetFinal);
        movies.addNewMovie(newMovie);
        return;
    }

    /**<u>Purpose: Print a system message to the console</u>
     * @param message The {@code message} to print
     */
    public static void printMessage(String message){
        System.out.println( message );
    }


    /**<u>Purpose: Find a movie in the system</u>
     * <br><b>Sub-function of: {@link #rentMovie(String, String, String, int, String)},
     * {@link #removeMovie(String, int, String)}, {@link #hasRemoveMovieErrors(Movie)}
     * {@link #updateMovieAndCustomer(Movie, Customer)}, {@link #isExistingMovieRented(Movie)}</b>
     *
     * @param movie The {@code movie} object to find
     * @return The actual movie object from the system
     */

    private Movie findMovie( Movie movie ){
        return this.movies.findMovie( movie );
    }

    /**<u>Purpose: Find a customer in the system</u>
     * <br><b>Sub-function of: {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customer The {@code customer} to find
     * @return The actual customer from the system
     */
    private Customer findCustomer(Customer customer){
        return this.customers.findCustomer(customer);
    }

    /**<u>Purpose: Check if a customer has already rented a specific movie</u>
     * <br><b>Sub-function of: {@link #checkIfCustomerAlreadyRentedMovie(Customer, Movie)}</b>
     * @param customer {@code customer} to check
     * @param movie {@code movie} to check
     * @return true if rented, otherwise false
     */
    private boolean isCustomerRentedMovie( Customer customer ,Movie movie){
        return customer.isMovieRented(movie);
    }


    /**<u>Purpose: Check if a customer exists in the system</u>
     * <br><b>Sub-function of: {@link #isCustomerExist(Customer)},
     * {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customer The {@code customer} to check
     * @return true if exists, otherwise false
     */
    private boolean isCustomerExistInSystem( Customer customer){
        return this.customers.isCustomerExisting( customer );
    }

    /**<u>Purpose: Check if customers array has reached its limit</u>
     *<br> <b>Sub-function of: {@link #checkIfNewCustomerAndNoPlace(Customer)}</b>
     * @return true if full, otherwise false
     */
    private boolean isCustomersFull(){
        return this.customers.isFull();
    }


    /**<u>Purpose: Check if a movie exists in the system</u>
     * <br><b>Sub-function of: {@link #checkIfMovieNotExist(Movie)}</b>
     * @param movie {@code movie} to check
     * @return true if exists, otherwise false
     */
    private boolean isMovieExistInSystem( Movie movie ){
        return this.movies.isMovieExisting(movie);
    }

    /**<u>Purpose: Add a new customer to the system</u>
     * <br><b>Sub-function of: {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customer {@code customer} to add
     */
    private void addCustomer( Customer customer){
        this.customers.addNewCustomer( customer ) ;
    }

    /**<u>Purpose: Find the director of a specific movie</u>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param movie The {@code movie} to locate its director
     * @return The {@code director} object
     */
    private Director findDirector( Movie movie ){
        return this.directors.findDirector( movie );
    }

    /**<u>Purpose: Update movie and customer records when renting</u>
     * <br><b>Used functions: {@link #findMovie(Movie)}, {@link #findCustomer(Customer)},
     * {@link #addCustomer(Customer)}</b>
     * <br><b>Sub-function of: {@link #rentMovie(String, String, String, int, String)}</b>
     * @param movie The {@code movie} to update
     * @param customer The {@code customer} to update
     */
    private void updateMovieAndCustomer( Movie movie, Customer customer ){
        if( !isCustomerExistInSystem( customer ) ){
            this.addCustomer(customer);
        }

        Movie realMovie = this.findMovie(movie);
        Customer realCustomer=  this.findCustomer(customer);

        realMovie.addCustomer( customer );
        realCustomer.addMovie( movie );
    }

    /**<p><u>Purpose: Check if a movie isn't existing in the system</u></p>
     * <p><b>Used functions: {@link #isMovieExistInSystem(Movie)}</b></p>
     * @param movie a movie to check
     * @return true if the movie is existing, otherwise false*/
    private boolean checkIfMovieNotExist( Movie movie) {
        if (!isMovieExistInSystem(movie)) {
            RentalSystem.printMessage(RentalSystem.movieIsnNotExistMessage);
            return true;
        }
        return false;
    }

    /**<u>Purpose: Wrapper for {@link #isCustomerExistInSystem(Customer)}</u>
     * <br><b>Sub-function of: {@link #checkIfNewCustomerAndNoPlace(Customer)}</b>
     * @param customer {@code customer} to check
     * @return true if exists
     */
    private boolean isCustomerExist( Customer customer){
        return this.isCustomerExistInSystem( customer );
    }


    /**<u>Purpose: Check if new customer can't be added due to system limit</u>
     * <br><b>Used functions: {@link #isCustomerExist(Customer)}, {@link #isCustomersFull()}</b>
     * <br><b>Sub-function of: {@link #hasRentMovieErrors(Movie, Customer)}</b>
     * @param customer The {@code customer} to check
     * @return true if new and no place, otherwise false
     */
    private boolean checkIfNewCustomerAndNoPlace( Customer customer ){
        if ( !isCustomerExist( customer ) && this.isCustomersFull() ) {
            RentalSystem.printMessage((RentalSystem.customerReachedLimitMessage));
            return true;
        }
        return false;
    }


    /**<u>Purpose: Check if customer already rented the specified movie</u>
     * <br><b>Used functions: {@link #isCustomerRentedMovie(Customer, Movie)}</b>
     * <br><b>Sub-function of: {@link #hasRentMovieErrors(Movie, Customer)}</b>
     * @param customer The {@code customer}
     * @param movie The {@code movie}
     * @return true if already rented
     */
    private boolean checkIfCustomerAlreadyRentedMovie( Customer customer, Movie movie){
        if( isCustomerRentedMovie( customer, movie ) ){
            printMessage(RentalSystem.customerAlreadyRentedMessage);
            return true;
        }
        return false;
    }

    /**<u>Purpose: Validate errors before renting a movie</u>
     * <br></vr><b>Used functions: {@link #checkIfMovieNotExist(Movie)}, {@link #checkIfNewCustomerAndNoPlace(Customer)}, {@link #checkIfCustomerAlreadyRentedMovie(Customer, Movie)}</b>
     * <br><b>Sub-function of: {@link #rentMovie(String, String, String, int, String)}</b>
     * @param movie {@code movie} object
     * @param customer {@code customer} object
     * @return true if any error occurs, otherwise false
     */
    private boolean hasRentMovieErrors( Movie movie, Customer customer ){
        return checkIfMovieNotExist(movie) ||
               checkIfNewCustomerAndNoPlace( customer) ||
               checkIfCustomerAlreadyRentedMovie( customer, movie);
    }


    /**<u>Purpose: Check if an existing movie is currently rented</u>
     * <br><b>Used functions: {@link #findMovie(Movie)}</b>
     * <br><b>Sub-function of: {@link #hasRemoveMovieErrors(Movie)}</b>
     * @param movie {@code movie} to check
     * @return true if rented, otherwise false
     */
    private boolean isExistingMovieRented( Movie movie ){
        Movie realMovie = this.findMovie( movie );
        if( realMovie.isRented() ){
            RentalSystem.printMessage( RentalSystem.cannotRemoveMovieMessage );
            return true;
        }
        return false;
    }


    /**<u>Purpose: Check if there are any errors that prevent movie removal</u>
     * <br><b>Used functions: {@link #checkIfMovieNotExist(Movie)}, {@link #isExistingMovieRented(Movie)}</b>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param movie {@code movie} to check
     * @return true if there are errors
     */
    private boolean hasRemoveMovieErrors( Movie movie) {
        return checkIfMovieNotExist( movie ) || isExistingMovieRented( movie );
    }


    /**
     * Prints movies based on their rental status.
     * Calls the method that prints rented movies and then the one that prints un rented movies.
     * If no movies match a status, prints the corresponding error message.
     *
     * @param isRented indicates whether to print rented (true) or un rented (false) movies
     * @param errorMessage the message to print if no movies match the status
     */
    public void printMovies(){

        printMoviesSub( true, RentalSystem.rentedMoviesArrMessage,
                RentalSystem.cannotFindRentedMovies);

        printMoviesSub( false, RentalSystem.unRentedMoviesArrMessage,
                RentalSystem.cannotFindUnRentedMovies);
    }

    private void printMoviesSub( boolean isRented, String message, String errorMessage){
        if( this.movies.hasRenteOrUnRented(isRented) ){
            RentalSystem.printMessage(message);
            movies.printMoviesByIsRented(isRented);
        } else{
            RentalSystem.printMessage(errorMessage);
        }
    }

    /**
     * Retrieves a customer by ID. If the customer is not found, an error message is printed.
     * A helper method for {@code returnMovie}
     *
     * @param id the ID of the customer
     * @return the Customer object if found, otherwise returns null
     */
    private Customer getValidCustomerOrPrintMessage(String id) {
        Customer customer = customers.findCustomerById(id);
        if (customer == null) {
            printMessage(customerNotFoundMessage);
        }
        return customer;
    }

    /**
     * Checks if the return operation is valid. A return is valid if the movie exists
     * and the customer has rented the movie.
     * If invalid, prints an appropriate error message.
     * A helper method for {@code returnMovie}
     *
     * @param movie the movie to return
     * @param customer the customer attempting to return the movie
     * @return true if the return is valid, false otherwise
     */
    private boolean isValidReturn(Movie movie, Customer customer) {
        if (movie == null || !customer.isMovieRented(movie)) {
            printMessage(customerCantReturnMessage);
            return false;
        }
        return true;
    }

    /**
     * Removes the movie from the customer's rented movies and removes the customer from the movie's renters.
     * If the customer no longer has any rented movies, the customer is removed from the system.
     *A helper method for {@code returnMovie}
     *
     * @param customer the customer returning the movie
     * @param movie the movie being returned
     */
    private void removeMovieFromCustomerAndSystem(Customer customer, Movie movie) {
        customer.removeMovie(movie);
        movie.removeCustomer(customer);

        if (customer.isRentedMoviesEmpty()) {
            this.customers.removeCustomer(customer);
        }
    }

    /**
     * Attempts to return a movie rented by a customer based on the provided details.
     * If the customer or movie is not found, or the movie was not rented by the customer,
     * appropriate error messages are printed and the method exits.
     *
     * @param id the ID of the customer
     * @param movieName the name of the movie to be returned
     * @param releaseYear the release year of the movie
     * @param directorName the name of the director of the movie
     */
    public void returnMovie (String id , String movieName , int releaseYear , String directorName ) {
        Movie foundMovie= movies.findMovieByNameReleaseYearAndDirectorName (movieName, releaseYear,  directorName);

        Customer foundCustomer = getValidCustomerOrPrintMessage(id);
        if (foundCustomer == null) {
            return;
        }
        if (!isValidReturn(foundMovie, foundCustomer)) {
            return;
        }

        removeMovieFromCustomerAndSystem(foundCustomer, foundMovie);
    }


}




