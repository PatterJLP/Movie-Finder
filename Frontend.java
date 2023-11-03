import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/*
 * A frontend for the movie timer app to select specific movies from a movie dataset.
 */
public class Frontend implements FrontendInterface {


    /**
     * Constructor for the IndividualFrontendInterface class which accepts a reference to the
     * backend and a java.util.Scanner instance to read user input.
     */
    public Frontend(BackendInterface backend, Scanner scanner) {
        System.out.println("Welcome to the Movie Database App!");
        scanner = new Scanner(System.in);

    }


    /**
     * A method to interactively loop prompting the user to select a command and input their data.
     * @throws FileNotFoundException
     */
    public static void fileLoop(BackendInterface backend, Scanner scanner) {
        // Prompt user for file path
        System.out.print("Please enter a file path: ");
        String filePath = scanner.nextLine();

        try {
            backend.readFile(filePath);
            // if we get here, the file was read successfully
            promptLoop(backend, scanner);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please enter a valid file path.");
            fileLoop(backend, scanner);
        }
    }


    /**
     * A method to interactively loop prompting the user to select a command and input their data.
     */
    public static void promptLoop(BackendInterface backend, Scanner scanner) {

        // Prompt user for command
        System.out.print("Command Options\n" + "#1. List movies with shortest duration. \n"
        + "#2. List movies between two time thresholds. \n" + "#3. Exit app.\n" + "Select a command: ");

        String command = scanner.nextLine();

        switch (command) {
            case "1": // Case 1: User selected the min movie duration command
                System.out.println("You selected: List movies with shortest minimum duration. \n");

                System.out.println("Please enter a minimum movie duration (minutes): ");
                int minLength = scanner.nextInt();

                List<MovieInterface> moviesWithMinDuration = backend.getMoviesWithMinDuration(minLength);

                System.out.println("Movies with a minimum duration of " + minLength + " minutes:");
                printMoviesWithMinDuration(moviesWithMinDuration);
             
                break;

            case "2": // Case 2: User selected movie duration between two thresholds command
                System.out.println("You selected: List movies between two time thresholds.\n");

                // Get lower and upper thresholds
                System.out.print("Please enter a lower threshold (minutes): ");
                int lower = scanner.nextInt();

                System.out.print("Please enter an upper threshold (minutes): ");
                int upper = scanner.nextInt();

		if (lower == upper) {
                    System.out.println("Error: Thresholds cannot be the same value. Please enter a valid range.");
                    promptLoop(backend, scanner);
                }
		
                // Now call backend to get the movies in that range
                List<MovieInterface> moviesInRange = backend.getMoviesWithinRange(lower, upper);
                System.out.println("Movies between " + lower + " minutes and " + upper + " minutes:");
                printMoviesBetweenRange(moviesInRange);

                break;
            case "3":
                quit();
                break;
            default:
                System.out.println("Error: Invalid command. Please enter a valid numeric command.");
                promptLoop(backend, scanner);
                break;
        }
    }


    /**
     * A method to print the movies with a minimum duration.
     */
    public static void printMoviesWithMinDuration(List<MovieInterface> movies) {
         for (MovieInterface movie : movies) {
            System.out.println(movie.getTitle() + " " + movie.getDuration());
        }
    }


    /**
    * A method to print movies between two thresholds.
    */
    public static void printMoviesBetweenRange(List<MovieInterface> movies) {
        for (MovieInterface movie : movies) {
            System.out.println(movie.getTitle() + " " + movie.getDuration());
        }
    }
    

    /**
     * A method to quit the app.
     */
    public static void quit() {
        System.out.println("Exiting program. Goodbye!");
        return;
    }
}
