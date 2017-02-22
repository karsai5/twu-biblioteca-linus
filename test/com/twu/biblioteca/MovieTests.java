package com.twu.biblioteca;

import org.junit.Test;

/**
 * Created by linus on 23/2/17.
 */
public class MovieTests extends BaseTest {

    public static Movie STATION_AGENT;
    public static Movie BRAVE;

    @Override
    protected void initialiseDummyData() {
        STATION_AGENT = new Movie("The Station Agent", "2003", "Tom McCarthy");
        BRAVE = new Movie("Brave", "2012", "Mark Andrews");
        biblioteca.addRentable(STATION_AGENT);
        biblioteca.addRentable(BRAVE);
    }

    public void checkForMovieTitleText() {
        checkForString(STATION_AGENT.getTitle());
        checkForString(BRAVE.getTitle());
    }

    @Test
    public void check_movies_are_listed_correctly() {
        biblioteca.printRentables();
        checkForMovieTitleText();
    }

    @Test
    public void checkout_the_station_agent() {
        biblioteca.printRentables();
        checkForString(STATION_AGENT.getTitle());
        biblioteca.checkout(STATION_AGENT.getTitle());

        systemOutRule.clearLog();
        biblioteca.printRentables();
        checkForMissingString(STATION_AGENT.getTitle());
    }

    @Test
    public void return_the_station_agent() {
        STATION_AGENT.checkout();
        biblioteca.printRentables();
        checkForMissingString(STATION_AGENT.getTitle());
        biblioteca.checkin(STATION_AGENT.getTitle());

        systemOutRule.clearLog();
        checkForMissingString(STATION_AGENT.getTitle());
    }
}
