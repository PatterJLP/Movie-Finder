
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Backend implements BackendInterface{
    //movieTree that data will be inserted into
    private IterableMultiKeySortedCollectionInterface<MovieInterface> movieTree;
    /**
     * Main method for Backend class. Creates new backend and frontend that starts the main
     * command loop.
     */
    public static void main(String[] args) {
        // Create an instance of the backend
        BackendInterface backend = new Backend(new BackendIterableMultiKeySortedCollection<>());

        // Create an instance of the frontend and pass the backend reference
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        // Start the main command loop
        Frontend.fileLoop(backend,new Scanner(System.in));
        Frontend.promptLoop(backend, new Scanner(System.in));
    }
    /**
     * Constructor for Backend
     * @param redBlackTree instance of redBlackTree that we will be inserting data into
     */
    public Backend(IterableMultiKeySortedCollectionInterface<MovieInterface> redBlackTree){
        this.movieTree = redBlackTree;
    }
    /**
     * Reads data from a csv file and creates a Movie based on this data. Inserts that movie into
     * the tree
     * @param file the csv file to be read
     */
    @Override
    public void readFile(String file) throws FileNotFoundException {
        try {
            //Creates a BufferedReader with file we are going to read
            BufferedReader input = new BufferedReader(new FileReader(file));
            String currentLine;
            input.readLine();

            //ArrayList we will use when we parse "" in csv file.
            List<String> updatedValues = new ArrayList<>();

            //While there is still data to read
            while ((currentLine = input.readLine()) != null){
                //Splits csv line into data by , and inserts into an array
                String[] movieData = currentLine.split(",");

                for (int i = 0; i < movieData.length;i++){
                    //If index of array starts with " we know we need to combine indexes
                    if(movieData[i].startsWith("\"")){
                        //Removes " from string
                        String combinedString = movieData[i].substring(1);
                        //While it doesn't end with " we combine indexes of array
                        while (!movieData[i].endsWith("\"") && i < movieData.length -1){
                            i++;
                            combinedString += ("," + movieData[i]);
                        }
                        //When it ends with " we remove the quotations and know this is the end
                        // of our combined string
                        if (combinedString.endsWith("\"")){
                            combinedString = combinedString.substring(0,combinedString.length()-1);
                        }
                        //Add the string to our updated arraylist
                        updatedValues.add(combinedString);
                    //If it doesn't start with " then we know data is good
                    }else {
                        updatedValues.add(movieData[i].trim());
                    }
                }

                //Our arrayList should have 8 different types of info
                if (updatedValues.size() == 8){
                    String title = updatedValues.get(1).trim();
                    int year = Integer.parseInt(updatedValues.get(2).trim());
                    String genre = updatedValues.get(3).trim();
                    int duration = Integer.parseInt(updatedValues.get(4).trim());
                    String country = updatedValues.get(5).trim();

                    //Initializes movie and adds it to tree
                    Movie movie = new Movie(title,genre,country,year,duration);
                    movieTree.insertSingleKey(movie);
                }
                updatedValues.clear();
            }
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        }catch (IOException e){
            System.out.println("IOException");
        }
    }

    /**
     * Gets movies based off duration. Uses iterator to iterator through tree and find movies
     * @param minLength the minimum length the movie should be
     * @return list of movies with this minimum length
     */
    @Override
    public List<MovieInterface> getMoviesWithMinDuration(int minLength) {
        List<MovieInterface> moviesWithMinDuration = new ArrayList<>();
        Iterator<MovieInterface> iterator = movieTree.iterator();
        //No movie should be below zero minutes
        if (minLength < 0){
            return moviesWithMinDuration;
        }

        //While there is data in the tree
        while (iterator.hasNext()){
            MovieInterface movie = iterator.next();
            //If the duration of the movie is above threshold add it to list
            if (movie.getDuration() >= minLength){
                moviesWithMinDuration.add(movie);
            }
        }
        return moviesWithMinDuration;

    }

    /**
     * Gets movies based off of a specific threshold. Uses iterator to iterator through tree and
     * find movies
     * @param minLength the minimum length the movie should be
     * @param maxLength the maximum length the movie should be
     * @return list of movies with a certain threshold
     */
    @Override
    public List<MovieInterface> getMoviesWithinRange(int minLength, int maxLength) {
        List<MovieInterface> moviesWithinRange = new ArrayList<>();
        Iterator<MovieInterface> iterator = movieTree.iterator();
        //Neither length should be below zero
        if (minLength < 0 || maxLength < 0){
            return moviesWithinRange;
        }

        //While there is data in the tree
        while (iterator.hasNext()){
            MovieInterface movie = iterator.next();
            int duration = movie.getDuration();
            //If duration is in between two points add it to list
            if (duration >= minLength && duration <= maxLength){
                moviesWithinRange.add(movie);
            }
        }
        return moviesWithinRange;
    }
}
