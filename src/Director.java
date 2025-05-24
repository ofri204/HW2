public class Director {

    /** Director Properties*/
    private final String name ;
    private final String biography;
    private final Utility movies;

    /**Main Director Constructor*/
    public Director(String name, String biography, int moviesSize) {
        this.name = name;
        this.biography = biography;
        this.movies = new Utility(moviesSize, Movie.class);
    }


    /**
     * Return the name of the director
     * @return name of director
     * */
    public String getName() {
        return this.name;
    }

    /**<p>Checks if a director is identical to the current</p>
     * <p><b>Note: 2 directors are identical if they have the same name</b></p>
     * @param director a director
     * @return true if the directors are identical, otherwise false*/
    @Override
    public boolean equals( Object director ){
        return this.isObjKindOf(director) && this.name.equals( ((Director)director).getName());
    }

    /**
     * Checks if an object is Director
     * @param obj object which can be a director
     * @return true if the object is a director, false otherwise
     * */
    public final boolean isObjKindOf( Object obj) {
        return Utility.isClassesIdentical(obj.getClass(), this.getClass());
    }

    /**
     * Removes the specified movie from the collection of movies.
     *
     * @param movie the movie to be removed
     */
    public void removeMovie( Movie movie ){
        this.movies.removeItem(movie);
    }

    /**
     * Checks if there are any movies in the array.
     * @return true if there is at least one movie; false if the array is empty
     */
    public boolean hasMovies(){
        return !this.movies.isArrEmpty();
    }

    /**
     * Add movie to movies of the director
     * @param movie movie of the director*/
    public void addMovie( Movie movie ){
        this.movies.addItem(movie);
    }


}