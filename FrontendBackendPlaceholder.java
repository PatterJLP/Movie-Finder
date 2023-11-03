import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/*
 * This class is a placeholder for the backend. 
 * The methods are subject to change to allow testing of the frontend.
 */
public class FrontendBackendPlaceholder implements BackendInterface{

    public void readFile(String file) throws FileNotFoundException {
        return;        
    }

    public List<MovieInterface> getMoviesWithMinDuration(int minLength) {
        List<MovieInterface> moviesWithMinDuration = new ArrayList<>();
        moviesWithMinDuration.add(new Movie("Shutter Island", "Thriller", "USA", 2009, 50));
        return moviesWithMinDuration;
    }

    public List<MovieInterface> getMoviesWithinRange(int minLength, int maxLength) {
        List<MovieInterface> moviesWithinRange = new ArrayList<>();
        moviesWithinRange.add(new Movie("Moana", "fantasy", "USA", 2016, 103));
        return moviesWithinRange;
    }

    
}
