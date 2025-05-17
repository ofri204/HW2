public class Director {

    private String name ;
    private String biography;
    private MoviesSet movies;


    public Director(String name, String biography, int defaultInitialMoviesSize,
                    boolean isMovieLengthLimited) {
        this.name = name;
        this.biography = biography;
        this.movies = new MoviesSet(defaultInitialMoviesSize, isMovieLengthLimited);
    }

    public String getName() {
        return this.name;
    }

    /**<p>Checks if a director is identical to the current</p>
     * <p><b>Note: 2 directors are identical if they have the same name</b></p>
     * @param director a director
     * @return true if the directors are identical, otherwise false*/
    public boolean isEquals( Director director ){
        return this.name.equals(director.getName());
    }

    /**
     * Removes the specified movie from the collection of movies.
     *
     * @param movie the movie to be removed
     */
    public void removeMovie( Movie movie ){
        this.movies.removeMovie(movie);
    }

    /**
     * Checks if there are any movies in the collection.
     *
     * @return true if there is at least one movie; false if the collection is empty
     */
    public boolean hasMovies(){
        return !this.movies.isEmpty();
    }

}