public class Movie implements MovieInterface,Comparable<MovieInterface>  {
    private String title;
    private String genre;
    private String country;
    private int year;
    private int duration;

    /**
     * Constructor for movie class
     */
    public Movie(String title, String genre, String country, int year, int duration){
        this.title = title;
        this.genre = genre;
        this.country = country;
        this.year = year;
        this.duration = duration;
    }

    /**
     * gets the title of the movie.
     * @return a string with the movie title
     */
    @Override
    public String getTitle(){
        return title;

    }

    /**
     * gets the genre of the movie.
     * @return the genre of movie
     */
    @Override
    public String getGenre(){
        return genre;

    }

    /**
     * gets the country that the movie was made in.
     * @return the country the movie is from
     */
    @Override
    public String getCountry(){
        return country;

    }

    /**
     * gets the year the movie was made.
     * @return the year the movie was made
     */
    @Override
    public int getYear(){
        return year;

    }

    /**
     * gets the duration of the movie.
     * @return the duration of the movie
     */
    @Override
    public int getDuration(){
        return duration;

    }
    /**
     * compares the duration of two different movies
     * @param other the other movie to be compared to
     */
    @Override
    public int compareTo(MovieInterface other) {
        if (this.duration > other.getDuration()){
            return 1;
        }else if (this.duration < other.getDuration()){
            return -1;
        }else {
            return 0;
        }

    }
}
