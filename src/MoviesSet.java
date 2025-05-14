public class MoviesSet {

    /** <p>Different system messages</p>*/
    private static final String initialRentedMovieMessage = "Rented Movies:";
    private static final String initialUnRentedMovieMessage = "Unrented Movies:";
    private static final String noRentedMoviesMessage = "No Rented movies.";
    private static final String noUnRentedMoviesMessage = "No Unrented movies.";

    /** empty string for biography of directors*/
    private static final String emptyBiography = "";
    /**<p>Different System Errors</p>*/
    private static final int movieIsFullError = -1;
    private static final int movieIsExistingError = -2;
    private static final int movieIsNotExistingError = -3;
    private static final int movieIsRentedError = -4;
    private static final int functionCompletedSuccessfully = 0;
    /** array of movies */
    private Movie[] movies;
    /** length of {@code movies} */
    private int size;

    /** array of all rented movies*/
    private Movie[] rentedMovies;
    /** array of all unrented movies*/
    private Movie[] unRentedMovies;

    /** <p>number of movies in {@code movies} which aren't nulls</p>
     * <b>Note: {@code activeMovies}-1 is the last position of a movie in {@code movies} which
     * isn't null, if {@code activeMovies} > 0. </b>*/
    private int activeMovies;

    /** <p>number of movies in {@code rentedMovies} which aren't nulls</p>
     * <p><b>Note 1: {@code activeMovies} - {@code activeRentedMovies} is the number of
     * all active unrented movies in {@code unRentedMovies}</b></p>
     * <p><b>Note 2: {@code activeRentedMovies}-1 and {@code activeMovies}
     * - {@code activeRentedMovies}-1 are last positions of rented and unrented movies in
     * {@code rentedMovies} and {@code unrentedMovies}</b></p>*/
    private int activeRentedMovies;

    /**<p>Main builder of MoviesSet Class</p>
     * @param size length of {@code movies} */
    public MoviesSet(int size){
        this.size = size;
        activeMovies = 0;
        activeRentedMovies = 0;
        movies = new Movie[this.size];
        rentedMovies = new Movie[this.size];
        unRentedMovies = new Movie[this.size];
    }

    /**
     * <p>Check is {@code movies} is empty</p>
     * <p><b>Note: {@code movies} is empty if it has nulls in all indexes</b></p>
     * @return 1 if {@code movies} is empty, otherwise 0
     */
    public boolean isEmpty(){
        return activeMovies == 0;
    }

    /**
     * <p>Check is {@code movies} is full</p>
     * <p><b>Note: {@code movies} is full if it hasn't any nulls</b></p>
     * @return 1 if {@code movies} is full, otherwise 0
     */
    public boolean isFull(){
        return(activeMovies == size);
    }

    /**
     * <p>Prints all movies details</p>
     * <p><b>Note: The printing separates the movies into 2 groups - rented and unrented</b></p>
     * */
    public void printMovies(){
        printRentedMovies();
        printUnRentedMovies();
    }

    /**
     * <p>Prints all rented Movies</p>
     */
    public void printRentedMovies(){
        System.out.println(initialRentedMovieMessage);
        for( int i=0; i < activeRentedMovies; i++){
            rentedMovies[i].printMovie();
        }
    }

    /**
     * <p>Prints all unrented Movies</p>
     */
    public void printUnRentedMovies(){
        System.out.println(initialUnRentedMovieMessage);
        for( int i=0; i < activeMovies - activeRentedMovies; i++){
            unRentedMovies[i].printMovie();
        }
    }

    /**
     *<p>Checks if a movie and the current movie are identical</p>
     * <p><b>Note: 2 movies are identical if they share the same name, releaseYear and director
     * </b></p>
     * @param name a of a movie
     * @param releaseYear a release year of a movie
     * @param director a director of a movie
     * @return true if the movies are identical, otherwise false
     * */
    private boolean isMovieExisting(String name, int releaseYear, Director director){
        for( int i=0; i < activeMovies; i++){
            if( movies[i].isEquals( name, releaseYear, director) ){
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Adds a movie to the System</p>
     * <p><b>Note: if {@code movies} is full, or a movie with identical details is existing
     * the movie won't be added</b></p>
     * @param name movie's name
     * @param genre movie's genre
     * @param releaseYear movie's release year
     * @param director movie's director
     * @return <p>{@code movieIsFullError} if {@code movies} is full</p>
     *         <p>{@code movieIsExistingError} if a movie with identical details is already existing
     *         in {@code movies}</p>
     *         <p>otherwise {@code functionCompletedSuccessfully}</p>
     * */
    public int addMovie(String name, EnumGenre genre, int releaseYear, Director director){
        if ( isFull() ){
            return movieIsFullError;
        } else if( isMovieExisting(name, releaseYear, director)){
            return movieIsExistingError;
        } else{
            movieAdder( name, genre, releaseYear, director);
        }
        return functionCompletedSuccessfully;
    }

    /**
     * <p>Adds a new movie to {@code Movies}</p>
     * <p><b>Note: Initially the movie is unrented</b></p>
     * @param name movie's name
     * @param genre movie's genre
     * @param releaseYear movie's release year
     * @param director movie's director
     * */
    private void movieAdder(String name, EnumGenre genre, int releaseYear, Director director){
        movies[activeMovies] = new Movie(name, genre, releaseYear, director);
    }

    /**
     * <p> Removes a movie from system</p>
     * <p><b>Note: if the movie is rented or isn't existing it won't be removed from system</b></p>
     * @param name movie's name
     * @param releaseYear movie's releaseYear
     * @param directorName movie's director name
     * @return <p>{@code movieIsNotExistingError} if the movie isn't existing in {@code movies}</p>
     *         <p>{@code movieIsRentedError} if the movie is rented by somebody</p>
     *         <p>otherwise {@code functionCompletedSuccessfully} </p>
     * */
    private int removeMovie( String name, int releaseYear, String directorName){
        if( isMovieExisting(name, releaseYear, new Director(directorName, emptyBiography)) ){
            return movieIsNotExistingError;
        }
        int found = findMovieByDetailsInRented(name, releaseYear, directorName);

        if( found != movieIsNotExistingError && isMovieRented( found) ){
            return movieIsRentedError;
        }

        movieRemover( name, releaseYear, directorName);
        return functionCompletedSuccessfully;

    }

    /**
     * <p>Checks if movie is rented by a customer</p>
     * <p><b>Note: {@code movieIndex} is the index in {@code movies}</b></p>
     * @param movieIndex index of a movie in {@code movies}
     * @return true if the movie is rented, otherwise false;
     * */
    private boolean isMovieRented( int movieIndex){
        return movies[movieIndex].isRented();
    }

    /**
     * <p>Removes a movie from the system</p>
     * <p><b>Note 1: movie can be removed if it is not rented</b></p>
     * <p><b>Note 2: movie is removed from {@code movies} and {@code unrentedMovies}</b></p>
     * @param name name of the movie which is removed
     * @param releaseYear release year of the movie which is removed
     * @param directorName director name of the movie which is removed
     * */
    private void movieRemover( String name, int releaseYear, String directorName){
        removeMovieFromMovies( findMovieByDetailsInMovies(name, releaseYear, directorName) );
        removeMovieFromUnrented( findMovieByDetailsInUnrented(name, releaseYear, directorName));
    }

    private static void removeMovieFromArr( Movie[] moviesArr, int activeMoviesInArr,
                                            int removeIndex ){
        moviesArr[removeIndex] = null;
        for( int i = removeIndex; i < activeMoviesInArr && i+1 < activeMoviesInArr; i++){
            moviesArr[i] = moviesArr[i+1];
        }
    }

    /**
     * <p>Removes movie from {@code movies}</p>
     * @param removedMovieIndex index of the movie which is removed*/
    private void removeMovieFromMovies( int removedMovieIndex){
        removeMovieFromArr(this.movies, this.activeMovies, removedMovieIndex);
    }

    /**
     * <p>Removes movie from {@code unRentedMovies}</p>
     * @param removedMovieIndex index of the movie which is removed*/
    private void removeMovieFromUnrented( int removedMovieIndex){
        removeMovieFromArr(this.unRentedMovies, this.activeMovies - this.activeRentedMovies
                , removedMovieIndex);

    }

    private void removeMovieFromRented( int removedMovieIndex){
        removeMovieFromArr(this.rentedMovies, this.activeRentedMovies, removedMovieIndex);

    }


    public int findMovieByDetailsInRented( String name, int releaseYear, String directorName ) {
        return findMovieByDetailsSub(rentedMovies, activeRentedMovies, name, releaseYear,
                directorName);
    }

    /**
     * <p>Find index of a movie in {@code movies}</p>
     * @param name name of the searched movie
     * @param releaseYear release year of the searched movie
     * @param directorName director name of the searched movie
     * @return <p>index of the searched movie in {@code movies} if the movie is existing
     *          in {@code movies}</p>
     *          <p>otherwise, {@code movieIsNotExistingError}</p>
     * */
    public int findMovieByDetailsInMovies( String name, int releaseYear, String directorName ){
        return findMovieByDetailsSub(movies, activeMovies, name, releaseYear, directorName);
    }


    /**
     * <p>Find index of a movie in {@code unRentedMovies}</p>
     * @param name name of the searched movie
     * @param releaseYear release year of the searched movie
     * @param directorName director name of the searched movie
     * @return <p>index of the searched movie in {@code unRentedMovies} if the movie is existing
     *          in {@code unRentedMovies}</p>
     *          <p>otherwise, {@code movieIsNotExistingError}</p>
     * */
    public int findMovieByDetailsInUnrented( String name, int releaseYear, String directorName ){
        return findMovieByDetailsSub( unRentedMovies, activeMovies - activeRentedMovies,
                name, releaseYear, directorName);
    }

    private static int findMovieByDetailsSub( Movie[] movieArr, int activeMoviesInArr,
                                              String name, int releaseYear,
                                       String directorName){
        Movie searchedMovie = new Movie( name, null, releaseYear,
                new Director( directorName, emptyBiography));
        return findMovieByDetailsMain( movieArr, activeMoviesInArr ,
                searchedMovie);
    }


    private static int findMovieByDetailsMain( Movie[] movieArr, int activeMoviesInArr
            , Movie searchedMovie){
        int found = movieIsNotExistingError;
        for( int i = 0; i < activeMoviesInArr; i++){
            if( movieArr[i].isEquals( searchedMovie) ){
                found = i;
                break;
            }
        }
        return found;
    }
}
