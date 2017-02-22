package com.twu.biblioteca;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by linus on 23/2/17.
 */
public class MovieTests extends BaseTest {

    public static Movie STATION_AGENT;
    public static Movie BRAVE;
    public static User JEAN;

    @Override
    protected void initialiseDummyData() {
        JEAN = new User("222-24601", "password");
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
    public void check_movies_titles_are_listed_correctly() {
        biblioteca.printRentables();
        checkForMovieTitleText();
    }

    @Test
    public void movies_print_director_year_and_rating() {
        biblioteca.printRentables();
        checkForString(STATION_AGENT.getDirector());
        checkForString(STATION_AGENT.getYear());
        checkForString(Integer.toString(STATION_AGENT.getRating()));
        checkForString(BRAVE.getDirector());
        checkForString(BRAVE.getYear());
        checkForString(Integer.toString(BRAVE.getRating()));
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
        STATION_AGENT.checkout(JEAN);
        assertTrue("Station agent isn't checked out.", STATION_AGENT.isCheckedOut());
        STATION_AGENT.checkout(JEAN);
        biblioteca.checkin(STATION_AGENT.getTitle());
        assertFalse("Station agent didn't check in.", STATION_AGENT.isCheckedOut());
    }
}
