package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public class Movie extends Rentable {
    public static final int UNRATED = -1;

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
        this(title, year, director, -1);
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
        String result = String.format("%-40.40s %-30.30s %-10.10s %-10.10s", "\"" + title + "\"", director, year, rating);
        return result;
    }
}
