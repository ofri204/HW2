public class Director {
    private String name ;
    private String biography;
    private MoviesSet movies;
    private static final boolean isMovieLegthLimited = false;
    private static final int defaultIntialMoviesSize = 5;

    public Director(String name, String biography) {
        this.name = name;
        this.biography = biography;
        this.movies = new MoviesSet(defaultIntialMoviesSize, isMovieLegthLimited);
    }

    public Director( Director director){
        this(director.name, director.biography);
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
}