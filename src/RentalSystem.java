public class RentalSystem {
    private CustomerSet customers;
    private MoviesSet movies;
    private DirectorSet directors;

    private static final int maxMovieNum = 30;
    private static final int maxCustomerNum = 30;
    private static final boolean isMovieSetFinal = true;
    private static final boolean isCustomerSetFinal = true;
    private static final boolean isDirectorSetFinal = false;
    private static final boolean isCustomerMovieSetFinal = true;

    private static final String customerReachedLimitMessage = "The customer has reached the limit";
    private static final String customerAlreadyRentedMessage = "Customer already has this movie";
    private static final String customerNotFoundMessage = "Customer not found.";
    private static final String customerCantReturnMessage = "Customer cannot return the movie.";
    private static final String movieInSystemMessage = "Movie is already in the system.";
    private static final String noRentedMoviesMessage = "No Rented movies.";
    private static final String movieIsnNotExistMessage = "No such movie exists.";
    private static final String cannotRemoveMovieMessage = "Cannot remove rented movie.";


    public RentalSystem(){
        this.customers = new CustomerSet(maxCustomerNum, isCustomerSetFinal);
        this.directors = new DirectorSet(maxMovieNum, isDirectorSetFinal);
        this.movies = new MoviesSet(maxMovieNum, isMovieSetFinal);
    }

    public static void printMessage(String message){
        System.out.println( message );
    }


    public void rentMovie(String customerName, String customerId, String movieName, int releaseYear,
            String directorName) {
        Customer currentCustomer = new Customer(customerName, customerId,
                RentalSystem.maxCustomerNum, RentalSystem.isCustomerMovieSetFinal);

        Director currentDirector = new Director(
                directorName, null, 0, false);

        Movie currentMovie = new Movie(movieName, null, releaseYear, currentDirector, 0, false);

        if ( hasRentMovieErrors( currentMovie, currentCustomer) ) {
            return;
        }

        updateMovieAndCustomer( currentMovie, currentCustomer);
    }

    private void updateMovieAndCustomer( Movie movie, Customer customer ){
        if( !isCustomerExistInSystem( customer ) ){
            this.addCustomer(customer);
        }
        Movie currentMovie = this.findMovie(movie);
        Customer currentCustomer=  this.findCustomer(customer);

        currentMovie.addCustomer( customer );
        currentCustomer.addMovie( movie );

    }

    private Movie findMovie( Movie movie ){
        return this.movies.findMovie( movie );
    }

    private Customer findCustomer(Customer customer){
        return this.customers.findCustomer(customer);
    }

    private boolean hasRentMovieErrors( Movie movie, Customer customer ){
        if ( isMovieExistInSystem( movie ) ) {
            RentalSystem.printMessage(RentalSystem.movieIsnNotExistMessage);
            return true;
        } else if ( !this.isCustomerExistInSystem( customer ) ) {
           if ( this.isCustomersFull() ) {
               RentalSystem.printMessage((RentalSystem.customerReachedLimitMessage));
               return true;
           } else{
               return false;
           }
        } else {
            int index = this.customers.findCustomerByDetails( customer );
            if( isACustomerRentedAMovie(index, movie) ){
                printMessage(RentalSystem.customerAlreadyRentedMessage);
                return true;
            } else{
                return false;
            }
        }
    }


    private void addCustomer( Customer customer){
        this.customers.addNewCustomer( customer ) ;
    }

    private boolean isACustomerRentedAMovie( int index, Movie movie){
        return this.customers.hasCustomerRentedAMovie(index, movie);
    }

    private boolean isCustomerExistInSystem( Customer customer){
        return this.customers.isCustomerExisting( customer );
    }

    private boolean isCustomersFull(){
        return this.customers.isFull();
    }

    private boolean isMovieExistInSystem( Movie movie ){
        return this.movies.isMovieExisting(movie);
    }


}
