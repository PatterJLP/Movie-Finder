import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class BackendPlaceholderForFrontend implements BackendInterface {


    String filePath;
    public BackendPlaceholderForFrontend() {
        // Constructor goes here.
        this.filePath = filePath;
    }

    @Override
    public void readFile(String file) throws FileNotFoundException {
        if (file == null)
        {
           throw new FileNotFoundException("nothing in file");
        }
        System.out.println(file);
    }

    @Override
    public List<MovieInterface> getMoviesWithMinDuration(int min) {
        ArrayList<MovieInterface> movies = new ArrayList<>();
        Movie m1 = new Movie("toystory", "kids", "Usa", 2006, 90);
        movies.add(m1);

        return movies;

    }

    @Override
    public List<MovieInterface> getMoviesWithinRange(int minLength, int maxLength) {
        ArrayList<MovieInterface> movies = new ArrayList<>();
        Movie m1 = new Movie("toystory", "kids", "Usa", 2006, 90);
        movies.add(m1);

        return movies;
    }
}
