package com.twu.biblioteca;

/**
 * Created by linus on 20/2/17.
 */
public class Book extends Rentable {
    private final String author;
    private final String yearPublished;

    public Book(String title, String author, String yearPublished) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    @Override
    public String toString() {
        String result = String.format("%-40.40s %-30.30s %-30.30s", "\"" + title + "\"", author, yearPublished);
        return result;
    }

    public String getAuthor() {
        return author;
    }

    public String getYearPublished() {
        return yearPublished;
    }

}
