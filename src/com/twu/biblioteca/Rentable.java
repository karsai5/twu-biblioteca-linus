package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public abstract class Rentable {
    protected String title;
    protected String rentableType;

    protected User owner = null;

    public Rentable(String title, String type) {
        this.title = title;
        this.rentableType = type;
    }

    public abstract String toString();

    public String getRentableType() {
        return rentableType;
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
