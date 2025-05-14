public class MoviesSet {

    /** empty string for biography of directors*/
    private static final String emptyBiography = "";
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

    /**<p>Main builder of MoviesSet Class</p>
     * @param size length of {@code movies} */
    public MoviesSet(int size){
        this.size = size;
        this.activeMovies = 0;
        this.movies = new Movie[this.size];
    }

    /**
     * <p>Class getters</p>
     */
    public static int getMovieIsFullError(){ return movieIsFullError; }
    public static int getMovieIsExistingError(){ return movieIsExistingError; }
    public static int getMovieIsNotExistingError(){ return movieIsNotExistingError; }
    public static int getFunctionCompletedSuccessfully(){ return functionCompletedSuccessfully; }


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
        return this.activeMovies == size;
    }


    /**
     * <p><u>Purpose: Checks if a movie is in {@code movies}</u></p>
     * p><b>This is sub-function of {@link #addNewMovie(Movie)} </b></p>
     * @param movie a movie
     * @return true if the movie is in {@code movies}, otherwise false
     * */
    private boolean isMovieExisting( Movie movie ){
        for( int i=0; i < this.activeMovies; i++){
            if( this.movies[i].isEquals( movie ) ){
                return true;
            }
        }
        return false;
    }

    /**
     *<p><u>Purpose: Add new movie to {@code movies}</u></p>
     * <p><b>Note: if the new movie is existing in {@code movies} or there is no space for it in
     * {@code movies}, the new movie won't be added</b></p>
     * <p><b>Used functions: {@link #isFull()}, {@link #isMovieExisting(Movie)}, 
     * {@link #movieAdder(Movie)}</b></p>
     *
     * @param movie a new movie
     * @return <p>{@code movieIsFullError} if {@code movies} is full of movies</p>
     *          <p>{@code movieIsExistingError} if the movie is existing in {@code movies}</p>
     *          <p>otherwise, it returns {@code functionCompletedSuccessfully}</p>*/
    private int addNewMovie(Movie movie){
        if ( isFull() ){
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
        this.movies[activeMovies] = movie;
        activeMovies++;
    }



    /**
     * <p><u>Purpose: Checks if movie is rented by a customer</u></p>
     * <p><b>Note: {@code movieIndex} is the index in {@code movies}</b></p>
     * @param movieIndex index of a movie in {@code movies}
     * @return true if the movie is rented, otherwise false;
     * */
    private boolean isMovieRented( int movieIndex ){
        return this.movies[movieIndex].isRented();
    }


    /**
     * <p><u>Purpose: Finds movie in {@code movies}</u></p>
     * <p><b>Note: Movie can be detected by 3 parameters: name, director and release year</b></p>
     * @param movie movie to be found
     * @return <p>index of the searched movie in {@code movies} if it is existing in
     * {@code movies},</p> <p>otherwise -1</p>
     * */
    private int findMovieByDetails( Movie movie){
        int found = movieIsNotExistingError;
        for( int i = 0; i < this.activeMovies; i++){
            if( this.movies[i].isEquals( movie) ){
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
     * @param movie a movie to remove*/
    private void movieRemover( Movie movie ){
        int removeIndex = findMovieByDetails( movie );
        this.movies[ removeIndex ] = null;
        this.activeMovies--;
        for( int i = removeIndex; i < this.activeMovies; i++){
            this.movies[i] = this.movies[i+1];
        }
    }

}



