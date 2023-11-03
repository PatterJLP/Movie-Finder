import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests {
    private BackendIterableMultiKeySortedCollection testingTree = new BackendIterableMultiKeySortedCollection();
    private Backend testBackend;
    private FrontendInterface testFrontend;
    private TextUITester textUITester;

    /**
     * Test method for quitting command loop
     */
    @Test
    public void frontEndTestQuit(){
        try {
            //Creates new textUITester to test invalid input
            textUITester = new TextUITester("3");
            //Initialize backend and frontend
            testBackend = new Backend(testingTree);
            testBackend.readFile("testMovies.csv");
            testFrontend = new Frontend(testBackend,new Scanner(System.in));

            //Tests the output contains exiting the program.
            Frontend.promptLoop(testBackend,new Scanner("3"));
            String output = textUITester.checkOutput();
            assertTrue(output.contains("Exiting program. Goodbye!"));
        }catch (Exception e){
            fail();
        }

    }
    /**
     * Test method for invalid frontend arguments
     */
    @Test
    public void frontEndTestInvalid(){
        try {
            //Creates new textUITester to test invalid input
            textUITester = new TextUITester("5\n2\n20");
            //Initialize backend and frontend
            testBackend = new Backend(testingTree);
            testBackend.readFile("testMovies.csv");
            testFrontend = new Frontend(testBackend,new Scanner(System.in));

            //Tests the output contains error for invalid command.
            Frontend.promptLoop(testBackend, new Scanner("5\n1\n20"));
            String output = textUITester.checkOutput();
            assertTrue(output.contains("Error: Invalid command. Please enter a valid numeric command."));
        }catch (Exception e){
            fail();
        }
    }
    /**
     * Test method for frontend testPrintShortestMovies
     */
    @Test
    public void integrationTestPrintShortestMovies(){
        try {
            //Creates new textUITester to test output
            textUITester = new TextUITester("1\n3\n");
            //Initialize backend and frontend
            testBackend = new Backend(testingTree);
            testBackend.readFile("testMovies.csv");
            testFrontend = new Frontend(testBackend, new Scanner(System.in));

            //Tests the output for movies for at least 180 minutes
            Frontend.promptLoop(testBackend,new Scanner("1\n180\n"));
            String output = textUITester.checkOutput();
            assertTrue(output.contains("testMovie6 200\r\ntestMovie9 190"));
        }catch (Exception e){
            fail();
        }
    }
    /**
     * Test method for frontend printMoviesBetweenRange
     */
    @Test
    public void integrationTestPrintMoviesBetweenRange(){
        try {
            //Creates new textUITester to test output
            textUITester = new TextUITester("2\n40\n50");
            //Initialize backend and frontend
            testBackend = new Backend(testingTree);
            testBackend.readFile("testMovies.csv");
            testFrontend = new Frontend(testBackend, new Scanner(System.in));

            //Tests the output for movies in between 40 and 50 minutes
            Frontend.promptLoop(testBackend, new Scanner("2\n40\n50"));
            String output = textUITester.checkOutput();
            assertTrue(output.contains("testMovie7 42"));

        }catch (Exception e){
            fail();
        }
    }




    /**
     * Test method for the readFile method
     */
    @Test
    public void testReadFile() {
        try {
            //Initialize backend implementation
            testBackend = new Backend(testingTree);

            //Tests reading a valid file
            try {
                testBackend.readFile("testMovies.csv");
                //If reached then test passed
                assertTrue(true);
            } catch (FileNotFoundException e) {
                fail("fileNotFoundException should not have been thrown");
            }

            //Tests reading an invalid file
            try {
                testBackend.readFile("invalidFile");
                fail("Exception should have been thrown");
            } catch (FileNotFoundException e) {
                //If reached then test passed
                assertTrue(true);
            }
        }catch (Exception e){
            fail();
        }
    }

    /**
     * Test method for the getMoviesWithMinDuration method
     */
    @Test
    public void testGetMoviesWithMinDuration() {
        try {
            //Initialize backend implementation
            testBackend = new Backend(testingTree);

            //Tests that getMoviesWithMinDuration returns 5 movies over 120 minutes
            testBackend.readFile("testMovies.csv");
            List<MovieInterface> moviesOver120 = testBackend.getMoviesWithMinDuration(120);
            assertEquals(5, moviesOver120.size(), "Expected 5 movies over 120 minutes");

            //Tests that getMoviesWithMinDuration returns 0 movies over 9999 minutes
            List<MovieInterface> moviesOver9999 = testBackend.getMoviesWithMinDuration(9999);
            assertEquals(0, moviesOver9999.size(), "Expected 0 movies over 9999 minutes");

            //Tests that getMoviesWithMinDuration returns all movies over 0 minutes
            List<MovieInterface> moviesOver0 = testBackend.getMoviesWithMinDuration(0);
            assertEquals(10, moviesOver0.size(), "Expected 10 movies over 0 minutes");


            //Tests that getMoviesWithMinDuration returns zero movies when file is empty
            BackendIterableMultiKeySortedCollection testingTreeEmpty = new BackendIterableMultiKeySortedCollection();
            testBackend = new Backend(testingTreeEmpty);
            testBackend.readFile("empty.csv");
            List<MovieInterface> emptyMovie = testBackend.getMoviesWithMinDuration(0);
            assertEquals(0, emptyMovie.size(), "Expected 0 movies since file is empty");
        }catch (Exception e){
            fail();
        }
    }

    /**
     * Test method for the getMoviesWithinRange method
     */
    @Test
    public void testGetMoviesWithinRange() {
        try {
            //Initialize backend implementation
            testBackend = new Backend(testingTree);

            //Tests getting movies within a range
            testBackend.readFile("testMovies.csv");
            List<MovieInterface> moviesWithinRange = testBackend.getMoviesWithinRange(120, 130);
            assertEquals(2, moviesWithinRange.size(), "There should be 2 movies in between 120 and " + "150 " + "minutes");

            //Tests getting movies in a range where there should be none
            List<MovieInterface> noMoviesWithinRange = testBackend.getMoviesWithinRange(700, 800);
            assertEquals(0, noMoviesWithinRange.size(), "There should be no movies in between 700 and " +
                    "800 " + "minutes");

            //Tests getting movies in a range where they should all be
            List<MovieInterface> allMoviesWithinRange = testBackend.getMoviesWithinRange(0, 300);
            assertEquals(10, allMoviesWithinRange.size(), "All movies should be within this range");
        }catch (Exception e){
            fail();

        }
    }

    /**
     * Test method for getting movies with negative duration
     */
    @Test
    public void testGetMoviesWithNegativeDuration() {
        try {
            //Initialize backend implementation
            testBackend = new Backend(testingTree);

            //Tests get min duration method with negative duration
            testBackend.readFile("testMovies.csv");
            List<MovieInterface> negativeDurationMovies = testBackend.getMoviesWithMinDuration(-100);
            assertEquals(0, negativeDurationMovies.size(), "There should be zero movies since " + "negative " + "duration is not allowed");

            //Tests get range method with two negative durations
            List<MovieInterface> rangeNegativeDurationMovies = testBackend.getMoviesWithinRange(-100, -10);
            assertEquals(0, rangeNegativeDurationMovies.size(), "There should be zero movies since " +
                    "negative " + "duration is not allowed");

        }catch (Exception e){
            fail();
        }

    }

    /**
     * Test method for the movie class.
     */
    @Test
    public void testMovieProperties() {
        //Creates new movie to test its properties
        Movie testMovie = new Movie("Test Movie", "Adventure", "US", 2010, 160);

        //Tests movie properties
        assertEquals("Test Movie", testMovie.getTitle());
        assertEquals("Adventure", testMovie.getGenre());
        assertEquals("US", testMovie.getCountry());
        assertEquals(2010, testMovie.getYear());
        assertEquals(160, testMovie.getDuration());
    }

}
