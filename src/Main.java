public class Main {

    public static void main(String[] args) {
        //test1_MaxRentals();
        //test2_ReturnNonExistent();
        //test3_DuplicateAdd();
        //test4_RemoveRentedOrNonExistent();
        text_6MaxClients();
    }

    public static void text_6MaxClients(){
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception");
        for( int i = 0; i < 30; i++){
            rentalSystem.rentMovie("John Doe", ""+i, "Inception", 2010, "Christopher Nolan");
        }

        for( int i = 0; i < 30; i++){
            rentalSystem.returnMovie( ""+i, "Inception", 2010, "Christopher Nolan");
        }

        rentalSystem.printMovies();
    }

    public static void test1_MaxRentals() {
        System.out.println("Test 1: Rent more than the allowed number of movies");
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception");
        rentalSystem.addMovie("The Dark Knight", Genre.ACTION, 2008, "Christopher Nolan", "Director of The Dark Knight");
        rentalSystem.addMovie("Interstellar", Genre.SCIENCE_FICTION, 2014, "Christopher Nolan", "Director of Interstellar");
        rentalSystem.addMovie("Dunkirk", Genre.ACTION, 2017, "Christopher Nolan", "Director of Dunkirk");
        rentalSystem.addMovie("Tenet", Genre.ACTION, 2020, "Christopher Nolan", "Director of Tenet");
        rentalSystem.addMovie("Memento", Genre.DRAMA, 2000, "Christopher Nolan", "Director of Memento");
        rentalSystem.addMovie("Prestige", Genre.DRAMA, 2006, "Christopher Nolan", "Director of Prestige");
        // Rent 5 movies
        rentalSystem.rentMovie("John Doe", "12345", "Inception", 2010, "Christopher Nolan");
        rentalSystem.rentMovie("John Doe", "12345", "The Dark Knight", 2008, "Christopher Nolan");
        rentalSystem.rentMovie("John Doe", "12345", "Interstellar", 2014, "Christopher Nolan");
        rentalSystem.rentMovie("John Doe", "12345", "Dunkirk", 2017, "Christopher Nolan");
        rentalSystem.rentMovie("John Doe", "12345", "Tenet", 2020, "Christopher Nolan");

        // Try renting one more movie (should fail)
        rentalSystem.rentMovie("John Doe", "12345", "Memento", 2000, "Christopher Nolan");

        //Try renting one of the movies again (should fail)
        rentalSystem.rentMovie("John Doe", "12345", "Inception", 2010, "Christopher Nolan");

        rentalSystem.printMovies();
    }

    public static void test2_ReturnNonExistent() {
        System.out.println("\nTest 2: Return a movie that is not in the system");
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception");
        rentalSystem.addMovie("The Dark Knight", Genre.ACTION, 2008, "Christopher Nolan", "Director of The Dark Knight");
        rentalSystem.addMovie("Interstellar", Genre.SCIENCE_FICTION, 2014, "Christopher Nolan", "Director of Interstellar");

        // Rent a movie
        rentalSystem.rentMovie("John Doe", "12345", "Inception", 2010, "Christopher Nolan");

        // Try to return a movie that is not in the system
        rentalSystem.returnMovie("12345", "Memento", 2000, "Christopher Nolan");

        // Return the rented movie
        rentalSystem.returnMovie("12345", "Inception", 2010, "Christopher Nolan");

        //Try to return the same movie again (should fail)
        rentalSystem.returnMovie("12345", "Inception", 2010, "Christopher Nolan");

        rentalSystem.printMovies();
    }

    public static void test3_DuplicateAdd() {
        System.out.println("\nTest 3: Add a duplicate movie");
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception");
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception"); // Duplicate
        rentalSystem.addMovie("The Dark Knight", Genre.ACTION, 2008, "Christopher Nolan", "Director of The Dark Knight");
        rentalSystem.addMovie("The Dark Knight", Genre.DRAMA, 2008, "Christopher Nolan", "Director of The Dark Knight"); //same name, different genre, should fail

        rentalSystem.printMovies();
    }

    public static void test4_RemoveRentedOrNonExistent() {
        System.out.println("\nTest 4: Remove a rented or non-existent movie");
        RentalSystem rentalSystem = new RentalSystem();
        rentalSystem.addMovie("Inception", Genre.SCIENCE_FICTION, 2010, "Christopher Nolan", "Director of Inception");
        rentalSystem.addMovie("The Dark Knight", Genre.ACTION, 2008, "Christopher Nolan", "Director of The Dark Knight");
        rentalSystem.addMovie("Interstellar", Genre.SCIENCE_FICTION, 2014, "Christopher Nolan", "Director of Interstellar");

        // Rent a movie
        rentalSystem.rentMovie("John Doe", "12345", "Inception", 2010, "Christopher Nolan");

        // Try to remove the rented movie (should fail)
        rentalSystem.removeMovie("Inception", 2010, "Christopher Nolan");

        // Try to remove a non-existent movie
        rentalSystem.removeMovie("NonExistent Movie", 1999, "Unknown Director");

        // Return the rented movie
        rentalSystem.returnMovie("12345", "Inception", 2010, "Christopher Nolan");

        // Remove the returned movie
        rentalSystem.removeMovie("Inception", 2010, "Christopher Nolan");

        rentalSystem.printMovies();
    }
}