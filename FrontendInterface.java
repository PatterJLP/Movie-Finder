/**
 * An interface for the frontend of the individual project.
 */
public interface FrontendInterface {

    /**
     * Constructor for the IndividualFrontendInterface class which accepts a reference to the
     * backend and a java.util.Scanner instance to read user input.
     */
    //public IndividualFrontendInterface(IndividualBackendInterface backend, Scanner scanner) {}

    /**
     * A method to interactively loop prompting the user to select a command and input their data.
     */
    public static void fileLoop() {}

    /*
     * A method that takes in the users commands after a file is successfully uploaded.
     */
    public static void promptLoop() {}

    /**
     * A method to print the shortest movies.
     */
    public static void printShortestMovies() {}

    /**
     * A method to print movies between two thresholds.
     */
    public static void printMoviesBetweenRange(int lower, int upper) {}

    /**
     * A method to quit the app.
     */
    public static void quit() {}

/*
Basic User Flow:
 * 
 * --------------------------------------------------
 * 
 * Please enter a file path: <filename>
 * 
 * Command Options
 * #1. List movies with shortest duration.
 * #2. List movies between two time thresholds.
 * #3. Exit app.
 * Select a command: <command>
 * 
 * --------------------------------------------------
 * 
 * Edge cases:
 * 
 * If they enter an invalid file path, then alert them and prompt them again.
 * If they select 1, then print out the movies with the shortest duration.
 * If they select 2, then prompt them for two time thresholds in minutes
 * and print out the movies between those two thresholds.
 * If they select 3, then exit the app.
 *
 */
}
