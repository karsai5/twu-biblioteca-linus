package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public abstract class Rentable {
    protected boolean checkedOut = false;
    protected String title;

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
