package com.twu.biblioteca;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by linus on 23/2/17.
 */
public class MovieTests extends BaseTest {

    public static Movie STATION_AGENT;
    public static Movie BRAVE;

    @Override
    protected void initialiseDummyData() {
    }

    @Test
    public void check_title_is_printed() {
        biblioteca.addRentable(new Movie("The Station Agent", "2003", "Tom McCarthy"));
        biblioteca.printRentables();
        checkForString("The Station Agent");
    }

    @Test
    public void check_year_and_director_are_printed() {
        biblioteca.addRentable(new Movie("The Station Agent", "2003", "Tom McCarthy"));
        biblioteca.printRentables();
        checkForString("Tom McCarthy");
        checkForString("2003");
    }

    @Test
    public void checkout_movie_via_biblioteca() {
        Movie stationAgent = new Movie("The Station Agent", "2003", "Tom McCarthy");
        biblioteca.addRentable(stationAgent);

        biblioteca.login(JEAN_USERNAME, JEAN_PASS);
        biblioteca.checkout("The Station Agent");

        assertTrue(stationAgent.isCheckedOut());
    }

    @Test
    public void return_movie_via_biblioteca() {
        Movie stationAgent = new Movie("The Station Agent", "2003", "Tom McCarthy");
        biblioteca.addRentable(stationAgent);

        stationAgent.checkout(mock(User.class));

        biblioteca.checkin("The Station Agent");
        assertFalse("Station agent didn't check in.", stationAgent.isCheckedOut());
    }
}
