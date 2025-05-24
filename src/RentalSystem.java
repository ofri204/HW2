public class RentalSystem {
    /**<p><u>General System Data Sets</u></p>*/
    private final Utility customers;
    private final Utility movies;
    private final Utility directors;

    /**<p><u>General Properties of Data Sets</u></p>*/
    private static final int MAX_MOVIE_NUM = 30;
    private static final int MAX_CUSTOMER_NUM = 30;
    private static final int MAX_DIRECTOR_NUM = 30;
    private static final int MAX_RENT_MOVIE_FOR_CUSTOMER = 5;

    /** <p><u>General System Messages</u></p>*/
    private static final String CUSTOMER_REACHED_LIMIT_MESSAGE = "The customer has reached the limit";
    private static final String CUSTOMER_ALREADY_RENTED_MOVIE = "Customer already has this movie";
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found.";
    private static final String CUSTOMER_CANT_RETURN_MESSAGE = "Customer cannot return the movie.";
    private static final String MOVIE_IN_SYSTEM_MESSAGE = "Movie is already in the system.";
    private static final String MOVIE_IS_NOT_IN_SYSTEM_MESSAGE = "No such movie exists.";
    private static final String CANNOT_REMOVE_MOVIE_MESSAGE = "Cannot remove rented movie.";
    private static final String CANNOT_ADD_MOVIE_MESSAGE = "Cannot add movie.";
    private static final String CANNOT_FIND_RENTED_MOVIES_MESSAGE = "No Rented movies.";
    private static final String CANNOT_FIND_UNRENTED_MOVIES_MESSAGE = "No Unrented movies.";
    private static final String RENTED_MOVIES_ARR_MESSAGE = "Rented Movies: ";
    private static final String UNRENTED_MOVIES_ARR_MESSAGE = "Unrented Movies: ";
    private static final String SYSTEM_IS_FULL_MESSAGE= "System is full, Cannot add more movies.";

    /**<p><u>RentalSystem Class Builder</u></p>*/
    public RentalSystem(){
        this.customers = new Utility(MAX_CUSTOMER_NUM, Customer.class );
        this.directors = new Utility(MAX_DIRECTOR_NUM, Director.class );
        this.movies = new Utility(MAX_MOVIE_NUM, Movie.class);
    }

    /**<u>Purpose: Rent an existing movie for a customer</u>
     * <p><b><br>Notes:
     * <br>- Movie must exist in system
     * <br>- Customer array must not be full if the customer is new
     * <br>- Customer cannot rent the same movie twice</b></p>
     * <b>Used functions: {@link #hasRentMovieInputErrors(Movie, Customer)}, {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customerName Customer's name
     * @param customerId Customer's ID
     * @param movieName Movie title
     * @param releaseYear Release year of the movie
     * @param directorName Director's name
     */
    public void rentMovie(String customerName, String customerId, String movieName, int releaseYear,
            String directorName) {
        Customer tempCustomer = new Customer(customerName, customerId,
                RentalSystem.MAX_RENT_MOVIE_FOR_CUSTOMER);

        Director tempDirector = new Director(directorName, null, RentalSystem.MAX_MOVIE_NUM);

        Movie tempMovie = new Movie(movieName, null, releaseYear, tempDirector,
                RentalSystem.MAX_CUSTOMER_NUM);

        //check for errors
        if ( hasRentMovieInputErrors( tempMovie, tempCustomer) ) {
            return;
        }

        //find the real references to the movie and the customer (both exist in system)
        Object tempNewCustomer = customers.findItemAndReturnReference( tempCustomer) ;
        Customer realCustomer = tempCustomer;
        Movie realMovie = (Movie)movies.findItemAndReturnReference( tempMovie ) ;

        if( tempNewCustomer != null ) {
            realCustomer = (Customer) tempNewCustomer;
        }

        if( realMovie == null){ return; }

        //add the movie to user, and the user to movie
        updateMovieAndCustomer( realMovie, realCustomer );
        this.addCustomer(realCustomer);
    }

    /**<u>Purpose: Validate errors before renting a movie</u>
     * <br></vr><b>Used functions: {@link #isMovieNotExistErrorOccur(Movie)},
     * {@link #isCustomerReachedLimitInRenting(Customer)},
     * {@link #isCustomerAlreadyRentedMovieErrorOccur(Customer, Movie)},
     * {@link #isMovieNotExistErrorOccur(Movie)},
     * {@link #findMovie(Movie)}, {@link #findCustomer(Customer)}</b>
     * <br><b>Sub-function of: {@link #rentMovie(String, String, String, int, String)}</b>
     * @param movie {@code movie} object
     * @param customer {@code customer} object
     * @return true if any error occurs, otherwise false
     */
    private boolean hasRentMovieInputErrors( Movie movie, Customer customer ) {
        //printing errors if needed
        boolean isMovieNotExist = this.isMovieNotExistErrorOccur(movie);

        //checks if movie doesn't exist
        if ( isMovieNotExist ) {
            return true;
        } else {
            //there are 2 additional checks of nulls because of the compiler needs. However, the
            //realCustomer and realMovie are existing in system and cannot be nulls

            //prints the error message if needed, checks if customer exist and not reached limit
            //or if the customer already rented the movie
            Customer realCustomer = this.findCustomer( customer );
            Movie realMovie = this.findMovie( movie );
            if( realCustomer != null &&
                    ( isCustomerAlreadyRentedMovieErrorOccur(realCustomer, realMovie) ||
                            this.isCustomerReachedLimitInRenting( realCustomer ) )){
                return true;
            }

            //customer doesn't exist and there is no space for him
            return realCustomer == null && this.isCustomersFull();
        }
    }

    /**<p><u>Purpose: Check if a movie isn't existing in the system</u></p>
     * <br><b>Used functions: {@link #isMovieExistInSystem(Movie)}</b>
     * <br><b>Sub-function of: {@link #hasRentMovieInputErrors(Movie, Customer)} </b>
     * @param movie a movie to check
     * @return true if the movie is existing, otherwise false*/
    private boolean isMovieNotExistErrorOccur( Movie movie ) {
        if ( movie == null || !this.isMovieExistInSystem(movie)) {
            RentalSystem.printMessage(RentalSystem.MOVIE_IS_NOT_IN_SYSTEM_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * <p><u>Checks if a customer in the system reached limit of renting</u></p>
     * <br><b>Sub function of: {@link #hasRentMovieInputErrors(Movie, Customer)}</b>
     * @param customer customer from system for checking
     * @return true if reached the limit, false otherwise
     * */
    private boolean isCustomerReachedLimitInRenting( Customer customer ){
        if( customer.isCustomerRentedMaxMovies() ){
            RentalSystem.printMessage( RentalSystem.CUSTOMER_REACHED_LIMIT_MESSAGE );
            return true;
        }
        return false;
    }

    /**<u>Purpose: Check if customer already rented the specified movie</u>
     * <br><b>Used functions: {@link #isCustomerRentedMovie(Customer, Movie)}</b>
     * <br><b>Sub-function of: {@link #hasRentMovieInputErrors(Movie, Customer)}</b>
     * @param customer The {@code customer}
     * @param movie The {@code movie}
     * @return true if already rented
     */
    private boolean isCustomerAlreadyRentedMovieErrorOccur( Customer customer, Movie movie){
        if( this.isCustomerRentedMovie( customer, movie ) ){
            printMessage(RentalSystem.CUSTOMER_ALREADY_RENTED_MOVIE);
            return true;
        }
        return false;
    }

    /**<u>Purpose: Update movie and customer records when renting</u>
     * <br><b>Note: Both parameters {@code movie} and {@code customer} are existing in system</b>
     * <br><b>Used functions: {@link #findMovie(Movie)}, {@link #findCustomer(Customer)},
     * {@link #addCustomer(Customer)}</b>
     * <br><b>Sub-function of: {@link #rentMovie(String, String, String, int, String)}</b>
     * @param movie The {@code movie} to update
     * @param customer The {@code customer} to update
     */
    private void updateMovieAndCustomer( Movie movie, Customer customer ){
        movie.addCustomer( customer);
        customer.addMovie( movie );
    }


    /**<u>Purpose: Remove a movie from the system if it's not currently rented</u>
     * <br><b>Used functions: {@link #hasRemoveMovieErrors(Movie)},
     * {@link #findMovie(Movie)}, {@link #findDirector(Movie)},
     * {@link #removeDirector(Director)}</b>
     * @param movieName Movie title
     * @param releaseYear Release year
     * @param directorName Director's name
     */
    public void removeMovie(String movieName, int releaseYear, String directorName){

        Director tempDirector = new Director( directorName, null, RentalSystem.MAX_MOVIE_NUM );
        Movie tempMovie = new Movie( movieName, null ,releaseYear, tempDirector,
                RentalSystem.MAX_CUSTOMER_NUM);

        //check errors
        if( this.hasRemoveMovieErrors( tempMovie ) ){ return; }

        //remove the movie from movies
        Movie realMovie = this.findMovie( tempMovie );

        //the movie does exist, just check for compiler
        if( realMovie == null){ return; }

        this.removeMovieFromArr(realMovie);

        Director movieDirector = this.findDirector( realMovie );

        if( movieDirector == null){ return; }
        movieDirector.removeMovie( realMovie ); //remove movie from director

        if( !movieDirector.hasMovies()){ //remove director if he hasn't any movies
            this.removeDirector( movieDirector );
        }
    }

    /**<u>Purpose: Check if an existing movie is currently rented</u>
     * <br><b>Sub-function of: {@link #hasRemoveMovieErrors(Movie)}</b>
     * @param movie {@code movie} to check
     * @return true if rented, otherwise false
     */
    private boolean isExistingMovieRented( Movie movie ){
        if( movie.isRented() ){
            RentalSystem.printMessage( RentalSystem.CANNOT_REMOVE_MOVIE_MESSAGE );
            return true;
        }
        return false;
    }


    /**<u>Purpose: Check if there are any errors that prevent movie removal</u>
     * <br><b>Used functions: {@link #isMovieNotExistErrorOccur(Movie)}, {@link #isExistingMovieRented(Movie)}</b>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param movie {@code movie} to check
     * @return true if there are errors
     */
    private boolean hasRemoveMovieErrors( Movie movie) {
        if( isMovieNotExistErrorOccur(movie)){ return true; }
        Movie realMovie = this.findMovie( movie ); // the movie exist
        return realMovie == null || isExistingMovieRented( realMovie );
    }

    /**<u>Purpose: Remove a director from system</u>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param director {@code director} to remove
     */
    private void removeDirector( Director director ){
        this.directors.removeItem( director );

    }

    /**
     * <p><u>Removes an existing unrented movie from system
     * @param movie an existing unrented movie from the system</u></p>
     * */
    private void removeMovieFromArr( Movie movie ){
        this.movies.removeItem( movie );
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
    public void addMovie(String movieName, Genre genre, int releaseYear, String directorName,
                         String biography) {

        //checks if all inputs aren't nulls
        if( this.isAddMovieInputInvalidString( movieName, genre, directorName, biography)){
            //printMessage(CANNOT_ADD_MOVIE_MESSAGE);
            return;
        }

        if(this.isSystemHasMaxMoviesNum()) {
            printMessage(SYSTEM_IS_FULL_MESSAGE);
            return;
        }

        Director tempDirector = new Director(directorName, biography, RentalSystem.MAX_MOVIE_NUM);
        Movie tempNewMovie = new Movie(movieName, genre, releaseYear,
                tempDirector, MAX_CUSTOMER_NUM);

        //checks if the movie is already existing in system
        if( this.isMovieExistInSystem(  tempNewMovie ) ){
            RentalSystem.printMessage(RentalSystem.MOVIE_IN_SYSTEM_MESSAGE);
            return;
        }

        //checks if the director already exist in the system
        Director realDirector = this.findDirector( tempDirector );
        if(  realDirector == null ){ //not exist
            realDirector = tempDirector;
            this.addDirector( realDirector );
        }

        Movie realNewMovie = new Movie(movieName, genre, releaseYear,
                realDirector, MAX_CUSTOMER_NUM);

        //adds the new movie to movies arr
        movies.addItem( realNewMovie );
        //adds the new movie to the director
        realDirector.addMovie( realNewMovie );
    }

    /**<p><u>Checks if director exist in the system</u></p>
     * @param director a director
     * @return the real director or null*/
    private Director findDirector( Director director ){
        Object tempDir = this.directors.findItemAndReturnReference( director );
        if( tempDir == null ) {
            return null;
        }
        return (Director)tempDir ;
    }

    /**
     * <p><u>Adds director to the system</u></p>
     * @param director a director to add */
    private void addDirector( Director director){
        this.directors.addItem( director );
    }

    /**
     * <p><u>Checks if the input of add movie function is invalid</u></p>
     * <br<b>Sub-function of: {@link #addMovie(String, Genre, int, String, String)}</b>
     * @param movieName the name of the movie
     * @param genre the genre of the movie
     * @param directorName the name of the movie's director
     * @param biography the biography of the director
     * @return true if there is an error, false otherwise
     * */
    private boolean isAddMovieInputInvalidString (String movieName, Genre genre,
                                                  String directorName, String biography) {
        return movieName == null || genre == null || directorName == null || biography == null ;
    }


    /**
     * Checks if the movies array of the system is full.
     * @return true if it is full, false otherwise
     */
    private boolean isSystemHasMaxMoviesNum() {
        return !this.movies.isArrNotFull();
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
     * {@link #updateMovieAndCustomer(Movie, Customer)},
     * {@link #hasRentMovieInputErrors(Movie, Customer)}</b>
     *
     * @param movie The {@code movie} object to find
     * @return The actual movie object from the system
     */
    private Movie findMovie( Movie movie ){
        Object temp = this.movies.findItemAndReturnReference( movie );
        if( temp == null){
            return null;
        }
        return (Movie)temp;
    }

    /**<u>Purpose: Find a customer in the system</u>
     * <br><b>Sub-function of: {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customer The {@code customer} to find
     * @return The actual customer from the system
     */
    private Customer findCustomer(Customer customer){
        Object temp = this.customers.findItemAndReturnReference(customer);
        if( temp == null ){
            return null;
        }
        return (Customer)temp;
    }

    /**<u>Purpose: Check if a customer has already rented a specific movie</u>
     * <br><b>Sub-function of: {@link #isCustomerAlreadyRentedMovieErrorOccur(Customer, Movie)}</b>
     * @param customer {@code customer} to check
     * @param movie {@code movie} to check
     * @return true if rented, otherwise false
     */
    private boolean isCustomerRentedMovie( Customer customer, Movie movie){
        return customer.isMovieRented(movie);
    }

    /**<u>Purpose: Check if customers array has reached its limit</u>
     * @return true if full, otherwise false
     */
    private boolean isCustomersFull(){
        return !this.customers.isArrNotFull();
    }


    /**<u>Purpose: Check if a movie exists in the system</u>
     * <br><b>Sub-function of: {@link #isMovieNotExistErrorOccur(Movie)}</b>
     * @param movie {@code movie} to check
     * @return true if exists, otherwise false
     */
    private boolean isMovieExistInSystem( Movie movie ){
        return this.movies.isItemExist(movie);
    }

    /**<u>Purpose: Add a new customer to the system</u>
     * <br><b>Sub-function of: {@link #updateMovieAndCustomer(Movie, Customer)}</b>
     * @param customer {@code customer} to add
     */
    private void addCustomer( Customer customer){
        this.customers.addItem( customer ) ;
    }

    /**<u>Purpose: Find the director of a specific movie</u>
     * <br><b>Sub-function of: {@link #removeMovie(String, int, String)}</b>
     * @param movie The {@code movie} to locate its director
     * @return The {@code director} object
     */
    private Director findDirector( Movie movie ){
        Director temp = movie.getDirector();
        return (Director) this.directors.findItemAndReturnReference( temp );
    }


    /**
     * Prints movies based on their rental status.
     * Calls the method that prints rented movies and then the one that prints un rented movies.
     * If no movies match a status, prints the corresponding error message.
     *
     */
    public void printMovies(){
        printMoviesSub( true, RentalSystem.RENTED_MOVIES_ARR_MESSAGE,
                RentalSystem.CANNOT_FIND_RENTED_MOVIES_MESSAGE);

        printMoviesSub( false, RentalSystem.UNRENTED_MOVIES_ARR_MESSAGE,
                RentalSystem.CANNOT_FIND_UNRENTED_MOVIES_MESSAGE);
    }

    /** Sub-function for printing all rented or unrented movies
     * @param isRented states if the system needs to print all rented or all unrented movies
     * @param message start message of the un/rented array display
     * @param errorMessage errorMessage that is displayed if there isn't any un/rented in the system
     * */
    private void printMoviesSub( boolean isRented, String message, String errorMessage){
        RentalSystem.printMessage(message);
        if( this.hasRentedOrUnRented(isRented) ){
            this.printMoviesByIsRented(isRented);
        } else{
            RentalSystem.printMessage(errorMessage);
        }
    }

    /**<p><u>Checks if system has un/rented movies</u></p>
     * @param isRented check un/rented movies
     * @return true if it has un/rented, false otherwise*/
    private boolean hasRentedOrUnRented( boolean isRented){
        int i = 0;
        Movie temp = (Movie)this.movies.returnItemByIndex(i);
        while( temp != null ){
            if( temp.isRented() == isRented ){
                return true;
            }
            i++;
            temp = (Movie)this.movies.returnItemByIndex(i);
        }
        return false;
    }

    /**
     * <p><u> Prints all un/rented movies in system</u></p>
     * @param isRented print un/rented movies*/
    private void printMoviesByIsRented( boolean isRented ) {
        int i = 0;
        Object temp = this.movies.returnItemByIndex(i);
        while( temp != null ){
            Movie movieTemp = (Movie)temp;
            if( movieTemp.isRented() == isRented ){
                movieTemp.printMovie();
            }
            i++;
            temp = this.movies.returnItemByIndex(i);
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

        Director tempDir  = new Director( directorName, null, RentalSystem.MAX_MOVIE_NUM);
        Movie tempMovie = new Movie( movieName, null ,releaseYear , tempDir,
                RentalSystem.MAX_CUSTOMER_NUM);
        Object foundMovie = movies.findItemAndReturnReference(tempMovie);
        Object foundCustomer =  customers.findItemAndReturnReference( new Customer(null, id,
                RentalSystem.MAX_RENT_MOVIE_FOR_CUSTOMER) );

        if (foundCustomer == null || foundMovie == null ) {
            if( foundCustomer == null){
                RentalSystem.printMessage( CUSTOMER_NOT_FOUND_MESSAGE);
                return;
            }

            RentalSystem.printMessage( RentalSystem.CUSTOMER_CANT_RETURN_MESSAGE);
            return;
        }

        Movie realMovie = (Movie) foundMovie;
        Customer realCustomer = (Customer) foundCustomer;

        if (!isValidReturn(realMovie, realCustomer)) {
            return;
        }

        removeMovieFromCustomerAndSystem(realCustomer, realMovie);

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
        if ( !customer.isMovieRented(movie)) {
            printMessage(CUSTOMER_CANT_RETURN_MESSAGE);
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

        if (!customer.isCustomerRentedMovies()) {
            this.customers.removeItem(customer);
        }
    }
}




