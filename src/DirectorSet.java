public class DirectorSet {
        /**<p>Different System Errors</p>*/
        private static final int directorIsFullError = -1;
        private static final int directorIsExistingError = -2;
        private static final int directorIsNotExistingError = -3;
        private static final int functionCompletedSuccessfully = 0;
        /** array of directors */
        private Director[] directors;
        /** length of {@code directors} */
        private int size;
        /** <p>number of directors in {@code directors} which aren't nulls</p>
         * <b>Note: {@code activeDirectors}-1 is the last position of a director in {@code directors} which
         * isn't null, if {@code activeDirectors} > 0. </b>*/
        private int activeDirectors;

        /**Specifies if {@code directors} will not be expanded*/
        private boolean isFinalSize;

        /**<p>Main builder of MoviesSet Class</p>
         * @param size length of {@code directors}
         * @param isFinalSize defines if the length of {@code directors} is final*/
        public DirectorSet(int size, boolean isFinalSize){
            this.size = size;
            this.activeDirectors = 0;
            this.directors = new Director[this.size];
            this.isFinalSize = isFinalSize;
        }

        /**
         * <p>Class getters</p>
         */
        public static int getDirectorIsFullError(){ return directorIsFullError; }
        public static int getDirectorIsExistingError(){ return directorIsExistingError; }
        public static int getDirectorIsNotExistingError(){ return directorIsNotExistingError; }
        public static int getFunctionCompletedSuccessfully(){ return functionCompletedSuccessfully;}


        /**
         * <p><u>Purpose: Check is {@code directors} is empty</u></p>
         * <p><b>Note: {@code directors} is empty if it has nulls in all indexes</b></p>
         * @return 1 if {@code directors} is empty, otherwise 0
         */
        public boolean isEmpty(){
            return this.activeDirectors == 0;
        }

        /**
         * <p><u>Purpose: Check is {@code directors} is full</u></p>
         * <p><b>Note: {@code directors} is full if it hasn't any nulls</b></p>
         * p><b>This is sub-function of {@link #addNewDirector(Director)} </b></p>
         * @return 1 if {@code directors} is full, otherwise 0
         */
        public boolean isFull(){
            return this.activeDirectors == size;
        }

        /**
         * <p><u>Purpose: Checks if a director is in {@code directors}</u></p>
         * p><b>This is sub-function of {@link #addNewDirector(Director)} </b></p>
         * @param director a director
         * @return true if the director is in {@code directors}, otherwise false
         * */
        public boolean isDirectorExisting( Director director ){
            return this.findDirectorByDetails( director ) != DirectorSet.directorIsNotExistingError;
        }

        /**
         *<p><u>Purpose: Add new director to {@code directors}</u></p>
         * <p><b>Note 1: if the new director is existing in {@code directors} or there is no space for it in
         * {@code directors}, the new director won't be added</b></p>
         * <p><b>Note 2: if {@code isFinalSize} is false, {@code directors} will be expanded</b></p>
         * <p><b>Used functions: {@link #isFull()}, {@link #isDirectorExisting(Director)},
         * {@link #directorAdder(Director)}</b></p>
         *
         * @param director a new director
         * @return <p>{@code directorIsFullError} if {@code directors} is full of directors</p>
         *          <p>{@code directorIsExistingError} if the director is existing in {@code directors}</p>
         *          <p>otherwise, it returns {@code functionCompletedSuccessfully}</p>*/
        public int addNewDirector(Director director){
            if ( this.isFinalSize && isFull() ){
                return directorIsFullError;
            } else if( isDirectorExisting( director )){
                return directorIsExistingError;
            } else{
                directorAdder( director );
                return functionCompletedSuccessfully;
            }
        }


        /**
         * <p><u>Purpose: Adds a director to {@code directors} directly</u></p>
         * <p><b>This is sub-function of {@link #addNewDirector(Director)}}</b></p>
         * @param director a director to add
         * */
        private void directorAdder(Director director){
            if( this.activeDirectors == this.size){
                expandDirectorArr();
            }
            this.directors[activeDirectors] = director;
            activeDirectors++;
        }

        /**
         * <p><u>Purpose: expands {@code customers} by 1 unit</u></p>
         * <p><b>This is sub-function of {@link #directorAdder(Director)}</b></p>
         * */
        private void expandDirectorArr(){
            this.size++;
            Director[] newArr = new Director[size];
            for(int i = 0; i < size - 1; i++){
                newArr[i] = this.directors[i];
            }
            this.directors = newArr;
        }


        /**
         * <p><u>Purpose: Finds director in {@code directors}</u></p>
         * <p><b>Note: director can be detected by 3 parameters: name, director and release year</b></p>
         * @param director director to be found
         * @return <p>index of the searched director in {@code directors} if it is existing in
         * {@code directors},</p> <p>otherwise -1</p>
         * */
        private int findDirectorByDetails( Director director){
            int found = directorIsNotExistingError;
            for( int i = 0; i < this.activeDirectors; i++){
                if( this.directors[i].isEquals( director) ){
                    found = i;
                    break;
                }
            }
            return found;
        }


        /**
         * <p><u>Purpose: Check if a director can be removed from {@code directors} and remove it
         * if so</u></p>
         * <p><b>Note: if the director isn't in {@code directors}, it won't be removed from
         * {@code directors} </b></p>
         * <p><b>Used functions: {@link #directorRemover(Director)}</b></p>
         * @param director a director to remove
         * @return {@code directorIsNotExistingError} if the director isn't existing in
         * {@code directors}, otherwise {@code functionCompletedSuccessfully}*/
        public int removeDirector( Director director){
            if( !isDirectorExisting(director) ){
                return directorIsNotExistingError;
            }

            directorRemover( director );
            return functionCompletedSuccessfully;
        }


        /**<p><u>Purpose: Remove a director from {@code directors} directly</u></p>
         * <p><b>This is sub-function of {@link #removeDirector(Director)}</b></p>
         * <p><b>Used functions: {@link #findDirectorByDetails(Director)}</b></p>
         * @param director a director to remove*/
        private void directorRemover( Director director ){
            int removeIndex = findDirectorByDetails( director );
            this.directors[ removeIndex ] = null;
            this.activeDirectors--;
            for( int i = removeIndex; i < this.activeDirectors; i++){
                this.directors[i] = this.directors[i+1];
            }
            this.directors[ this.activeDirectors + 1 ] = null;
        }

    public Director findDirector( Movie movie ){
        return this.directors[this.findDirectorByDetails( movie.getDirector() ) ] ;
    }

    public Director findDirector (Director director) {
        int directorIndex = findDirectorByDetails(director);
        if (directorIndex == directorIsNotExistingError) {
            return null;
        }
        else{
            return directors[directorIndex];
        }

    }

}




