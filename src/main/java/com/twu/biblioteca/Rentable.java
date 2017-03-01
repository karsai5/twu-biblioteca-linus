package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public abstract class Rentable {
    protected String title;
    protected String readableName;
    public enum RentableType {
        BOOK, MOVIE
    }
    protected RentableType rentableType;

    protected User owner = null;

    public Rentable(String title, String readableName, RentableType rentableType) {
        this.title = title;
        this.readableName = readableName;
        this.rentableType = rentableType;
    }

    public abstract String toString();

    public String getReadableName() {
        return readableName;
    }

    public boolean isRentableType(RentableType rentableType) {
        if (this.rentableType == rentableType) {
            return true;
        }
        return false;
    }

    public void checkout(User user) {
        if (user == null) {
            throw new IllegalArgumentException("You can't checkout a book with a null user.");
        }
        owner = user;
    }

    public void checkin() {
        owner = null;
    }

    public boolean isCheckedOut() {
        if (owner == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
