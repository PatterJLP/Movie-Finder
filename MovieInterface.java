/**
 *This interfaces defines a single movie and exposes movie properties
 */
public interface MovieInterface extends Comparable<MovieInterface>{
    /*
    Constructor for movie
    public Movie(String title, String genre, String country, int year, int duration);
     */

    /**
     * gets the title of the movie.
     * @return a string with the movie title
     */
    public String getTitle();

    /**
     * gets the genre of the movie.
     * @return the genre of movie
     */
    public String getGenre();

    /**
     * gets the country that the movie was made in.
     * @return the country the movie is from
     */
    public String getCountry();

    /**
     * gets the year the movie was made.
     * @return the year the movie was made
     */
    public int getYear();

    /**
     * gets the duration of the movie.
     * @return the duration of the movie
     */
    public int getDuration();
}
