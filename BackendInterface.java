import java.util.List;
import java.io.FileNotFoundException;
/**
 * This interfaces allows users to get data and lists of movies from a file
 */
public interface BackendInterface {
    /*
    Constructor for backendInterface
    public backendInterface(IterableMultiKeySortedCollection tree);
     */

    /**
     * Retrieves data from a file.
     * @param file
     */
    public void readFile(String file) throws FileNotFoundException;

    /**
     * Finds list of movies with minimum duration of movies.
     * @param minLength minimum duration of movies
     * @return a list of movies with the minimum duration.
     */
    public List<MovieInterface> getMoviesWithMinDuration(int minLength);
    /**
     * Finds list of movies between two specific thresholds
     * lists of objects in each node of the tree.
     * @param minLength minimum length of movie
     * @param maxLength maximum length of movie
     * @return list of movies with duration in between two thresholds
     */
    public List<MovieInterface> getMoviesWithinRange(int minLength, int maxLength);
}

