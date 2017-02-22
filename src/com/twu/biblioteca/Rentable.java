package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public abstract class Rentable {
    protected boolean checkedOut = false;
    protected String title;
    protected String rentableType;


    public Rentable(String title, String type) {
        this.title = title;
        this.rentableType = type;
    }

    public String getRentableType() {
        return rentableType;
    }

    public void checkout() {
        checkedOut = true;
    }

    public void checkin() {
        checkedOut = false;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }


    public String getTitle() {
        return title;
    }

    public abstract String toString();
}
