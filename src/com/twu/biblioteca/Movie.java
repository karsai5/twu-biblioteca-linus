package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public class Movie {
    public static final int UNRATED = -1;

    private final String title;
    private final String year;
    private final String director;

    private int rating = UNRATED;

    public Movie(String title, String year, String director, int rating) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
    }

    public Movie(String title, String year, String director) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = UNRATED;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                '}';
    }
}
