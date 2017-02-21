package com.twu.biblioteca;

/**
 * Created by linus on 20/2/17.
 */
public class Book {
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void checkout() {
        checkedOut = true;
    }

    private final String author;
    private final String title;
    private final String yearPublished;
    private boolean checkedOut = false;

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
}
