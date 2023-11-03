import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Scanner;


/**
 * This class contains tests for the FrontendDeveloper class.
 */
public class FrontendDeveloperTests {   

    /**
     * This test ensures that user input is received and that the correct command is executed.
     */
    @Test
    public void testCommandSelection1(){

	TextUITester tester = new TextUITester("test.txt\n1\n 10");
	
        
        FrontendBackendPlaceholder backend = new FrontendBackendPlaceholder();
        Scanner scnr = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scnr);

	frontend.fileLoop(backend, scnr);
	
        // Check output
	String output = tester.checkOutput();
	String expectedOutput =  "You selected: List movies with shortest minimum duration.";	                         
	assertTrue(output.contains(expectedOutput));
    }

    /*
     * This test ensures that the correct input outputs the associated command.
     */
    @Test
    public void testCommandSelection2(){

        TextUITester tester = new TextUITester("test.txt\n2\n10\n20");


        FrontendBackendPlaceholder backend = new FrontendBackendPlaceholder();
        Scanner scnr = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scnr);

        frontend.fileLoop(backend, scnr);

        // Check output
        String output = tester.checkOutput();
        String expectedOutput =  "You selected: List movies between two time thresholds.";
        assertTrue(output.contains(expectedOutput));
    }

    /*
     * This test tests edge cases in user input.
     */
    @Test
    public void testInvalidInput() {
	TextUITester tester = new TextUITester("test.txt\n invalid command \n 4 \n 31\n3\n");
        
        // Hardcoded input
        FrontendBackendPlaceholder backend = new FrontendBackendPlaceholder();
        Scanner scnr = new Scanner(System.in);
	Frontend frontend = new Frontend(backend, scnr);

	frontend.fileLoop(backend, scnr);
        
	String output = tester.checkOutput();
	
        // Check output      
	assertTrue(output.contains("Error: Invalid command. Please enter a valid numeric command.\n")); // this means it correctly handled invalid input.

    }

    /*
     * This test tests that prompts are printed correctly.
     */
    @Test
    public void testFileInput(){

	TextUITester tester = new TextUITester("movies.csv\n1\n10\n"); // if we can input this, the file is properly read.


        FrontendBackendPlaceholder backend = new FrontendBackendPlaceholder();
        Scanner scnr = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scnr);

        frontend.fileLoop(backend, scnr);

        // Check output
        String output = tester.checkOutput();
        assertTrue(output.contains("Select a command: ")); // If output contains this, we know the file path worked.
    }

    /*
     * This test tests that the program exits correctly.   
     */
    @Test
    public void testExitApp(){
	TextUITester tester = new TextUITester("test.txt\n3\n");


        FrontendBackendPlaceholder backend = new FrontendBackendPlaceholder();
        Scanner scnr = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scnr);

        frontend.fileLoop(backend, scnr);

        // Check output
        String output = tester.checkOutput();
        assertTrue(output.contains("Exiting program. Goodbye!\n"));
    }
}






