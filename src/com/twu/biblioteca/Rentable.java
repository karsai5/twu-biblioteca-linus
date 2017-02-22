package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public abstract class Rentable {
    protected boolean checkedOut = false;
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
        owner = user;
        checkedOut = true;
    }

    public void checkin() {
        owner = null;
        checkedOut = false;
    }

    public boolean isCheckedOut() {
        return checkedOut;
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
