public class RentalSystem {
    private CustomerSet customers;
    private MoviesSet rentedMovies;
    private MoviesSet unRentedMovies;
    private DirectorSet directors;

    private static final int maxMovieNum = 30;
    private static final int maxCustomerNum = 30;
    private static final boolean isMovieSetFinal = true;
    private static final boolean isCustomerSetFinal = true;
    private static final boolean isDirectorSetFinal = false;

    private static final String customerReachedLimitMessage = "The customer has reached the limit";
    private static final String customerAlreadyRentedMessage = "Customer already has this movie";
    private static final String customerNotFoundMessage = "Customer not found.";
    private static final String customerCantReturnMessage = "Customer cannot return the movie.";
    private static final String movieInSystemMessage = "Movie is already in the system.";
    private static final String noRentedMoviesMessage = "No Rented movies.";
    private static final String movieIsnNotExistMessage = "No such movie exists.";
    private static final String cannotRemoveMovieMessage = "Cannot remove rented movie.";


    RentalSystem(){
        this.customers = new CustomerSet(maxCustomerNum, isCustomerSetFinal);
        this.directors = new DirectorSet(maxMovieNum, isMovieSetFinal);
        this.rentedMovies = new MoviesSet(maxMovieNum, isMovieSetFinal);
        this.unRentedMovies = new MoviesSet(maxMovieNum, isMovieSetFinal);
    }





}
