public class MoviesSet {

    /**<p>Different System Errors</p>*/
    private static final int movieIsFullError = -1;
    private static final int movieIsExistingError = -2;
    private static final int movieIsNotExistingError = -3;
    private static final int functionCompletedSuccessfully = 0;

    /** array of movies */
    private Movie[] movies;
    /** length of {@code movies} */
    private int size;
    /** <p>number of movies in {@code movies} which aren't nulls</p>
     * <b>Note: {@code activeMovies}-1 is the last position of a movie in {@code movies} which
     * isn't null, if {@code activeMovies} > 0. </b>*/
    private int activeMovies;

    /**Specifies if {@code movies} will not be expanded*/
    private boolean isFinalSize;

    /**<p>Main builder of MoviesSet Class</p>
     * @param size length of {@code movies}
     * @param isFinalSize defines if the length of {@code movies} is final*/
    public MoviesSet(int size, boolean isFinalSize){
        this.size = size;
        this.activeMovies = 0;
        this.movies = new Movie[this.size];
        this.isFinalSize = isFinalSize;
    }

    public static int getMovieIsNotExistingError(){
        return movieIsNotExistingError;
    }
    /**
     * <p><u>Purpose: Check is {@code movies} is empty</u></p>
     * <p><b>Note: {@code movies} is empty if it has nulls in all indexes</b></p>
     * @return 1 if {@code movies} is empty, otherwise 0
     */
    public boolean isEmpty(){
        return this.activeMovies == 0;
    }


    /**
     * <p><u>Purpose: Check is {@code movies} is full</u></p>
     * <p><b>Note: {@code movies} is full if it hasn't any nulls</b></p>
     * p><b>This is sub-function of {@link #addNewMovie(Movie)} </b></p>
     * @return 1 if {@code movies} is full, otherwise 0
     */
    public boolean isFull(){
        return this.activeMovies >= size;
    }


    /**
     * <p><u>Purpose: Checks if a movie is in {@code movies}</u></p>
     * p><b>This is sub-function of {@link #addNewMovie(Movie)} </b></p>
     * @param movie a movie
     * @return true if the movie is in {@code movies}, otherwise false
     * */
    public boolean isMovieExisting( Movie movie ){
        return this.findMovieByDetails( movie ) != MoviesSet.movieIsNotExistingError;
    }

    /**
     * Finds and returns a movie from the collection that matches the given movie details.
     *
     * @param movie the Movie object containing the details to search for
     * @return the matching Movie object if found; null if not found or index is invalid
     */
    public Movie findMovie( Movie movie ){
        return this.movies[ this.findMovieByDetails( movie ) ];
    }

    /**
     *<p><u>Purpose: Add new movie to {@code movies}</u></p>
     * <p><b>Note 1: if the new movie is existing in {@code movies} or there is no space for it in
     * {@code movies}, the new movie won't be added</b></p>
     * <p><b>Note 2: if {@code isFinalSize} is false, {@code movies} will be expanded</b></p>
     * <p><b>Used functions: {@link #isFull()}, {@link #isMovieExisting(Movie)},
     * {@link #movieAdder(Movie)}</b></p>
     *
     * @param movie a new movie
     * @return <p>{@code movieIsFullError} if {@code movies} is full of movies</p>
     *          <p>{@code movieIsExistingError} if the movie is existing in {@code movies}</p>
     *          <p>otherwise, it returns {@code functionCompletedSuccessfully}</p>*/
    public int addNewMovie(Movie movie){
        if (  this.isFinalSize && isFull() ){
            return movieIsFullError;
        } else if( isMovieExisting( movie )){
            return movieIsExistingError;
        } else{
            movieAdder( movie );
            return functionCompletedSuccessfully;
        }
    }


    /**
     * <p><u>Purpose: Adds a movie to {@code movies} directly</u></p>
     * <p><b>This is sub-function of {@link #addNewMovie(Movie)}}</b></p>
     * @param movie a movie to add
     * */
    private void movieAdder(Movie movie){

        if( this.activeMovies == this.size ){
            expandMoviesArr();
        }
        this.movies[activeMovies] = movie;
        activeMovies++;
    }

    /**
     * <p><u>Purpose: expands {@code customers} by 1 unit</u></p>
     * <p><b>This is sub-function of {@link #movieAdder(Movie)}</b></p>
     * */
    private void expandMoviesArr(){
        this.size++;
        Movie[] newArr = new Movie[size];
        for(int i = 0; i < size - 1; i++){
            newArr[i] = this.movies[i];
        }
        this.movies = newArr;
    }


    /**
     * <p><u>Purpose: Finds movie in {@code movies}</u></p>
     * <p><b>Note: Movie can be detected by 3 parameters: name, director and release year</b></p>
     * @param movie movie to be found
     * @return <p>index of the searched movie in {@code movies} if it is existing in
     * {@code movies},</p> <p>otherwise -1</p>
     * */
    public int findMovieByDetails( Movie movie){
        int found = movieIsNotExistingError;
        for( int i = 0; i < this.activeMovies; i++){
            if( this.movies[i].isEquals( movie ) ){
                found = i;
                break;
            }
        }
        return found;
    }


    /**
     * <p><u>Purpose: Check if a movie can be removed from {@code movies} and remove it if so</u></p>
     * <p><b>Note: if the movie isn't in {@code movies}, it won't be removed from {@code movies}
     * </b></p>
     * <p><b>Used functions: {@link #movieRemover(Movie)}</b></p>
     * @param movie a movie to remove
     * @return {@code movieIsNotExistingError} if the movie isn't existing in {@code movies},
     * otherwise {@code functionCompletedSuccessfully}*/
    public int removeMovie( Movie movie){
        if( !isMovieExisting(movie) ){
            return movieIsNotExistingError;
        }

        movieRemover( movie );
        return functionCompletedSuccessfully;
    }


    /**<p><u>Purpose: Remove a movie from {@code movies} directly</u></p>
     * <p><b>This is sub-function of {@link #removeMovie(Movie)}</b></p>
     * <p><b>Used functions: {@link #findMovieByDetails(Movie)}</b></p>
     * @param movie a movie to remove*/
    private void movieRemover( Movie movie ){
        int removeIndex = findMovieByDetails( movie );
        this.movies[ removeIndex ] = null;

        for( int i = removeIndex; i < this.activeMovies - 1; i++){
            this.movies[i] = this.movies[i+1];
        }

        this.movies[ this.activeMovies - 1 ] = null;
        this.activeMovies--;
    }

    /**
     * Checks whether a movie should be printed based on its rental status.
     * A helper method for {@code printMoviesByIsRented}
     *
     * @param movie the movie to check
     * @param isRented true to check for rented movies, false for not rented ones
     * @return true if the movie's rental status matches the given flag, false otherwise
     */
    private boolean ShouldPrintMovie (Movie movie , boolean isRented){
        return movie.isRented()== isRented;
    }

    /**
     * Prints all movies that match the given rental status (rented or not).
     * If no movies match the status, prints the provided error message.
     * A helper method for {@code PrintMovies} at RentalSystem class
     *
     * @param isRented true to print rented movies, false to print available ones
     */
    public void printMoviesByIsRented ( boolean isRented){
        for( int i = 0; i< this.activeMovies; i++){
            if (ShouldPrintMovie(movies[i] , isRented)) {
                movies[i].printMovie();
            }
        }
    }

    /**
     * Checks if the set has rent or unrented item (depends on input)
     * @param isRented states if the function search for rented (true) or unrented (false)
     * @return true if un/rented movie found, false otherwise
     * */
    public boolean hasRentedOrUnRented( boolean isRented){
        for( int i = 0; i<activeMovies; i++){
            if( this.movies[i].isRented() == isRented){
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a movie in the system by its name, release year, and director's name.
     *
     * @param movieName the name of the movie to find
     * @param releaseYear the release year of the movie
     * @param directorName the name of the movie's director
     * @return the Movie object if found; null if no matching movie exists
     */
    public Movie findMovieByNameReleaseYearAndDirectorName( String movieName , int releaseYear,
                                                            String directorName){
        Director newDirector = new Director(directorName, null, 0, false);
        Movie newMovie = new Movie(movieName, null, releaseYear,  newDirector,
                0, false);

        int index = this.findMovieByDetails( newMovie );
        if( index != MoviesSet.movieIsNotExistingError){
            return this.movies[index];
        }

        return null;
    }

}



