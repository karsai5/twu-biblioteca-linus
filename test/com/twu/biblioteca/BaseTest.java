package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * Created by linus on 23/2/17.
 */
public abstract class BaseTest {
    protected BibliotecaApp biblioteca;
    protected User JEAN;
    protected String JEAN_USERNAME = "222-4601";
    protected String JEAN_PASS = "password";

    protected abstract void initialiseDummyData();

    // Used for grabbing System.out.println output so it can be asserted
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    // Used for emulating user input
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void setUpStreams() {
        biblioteca = new BibliotecaApp();
        biblioteca.clearRentables();
        biblioteca.addUser(JEAN = new User(JEAN_USERNAME, JEAN_PASS));
        initialiseDummyData();
    }

    protected void checkForString(String text) {
        assertTrue("Couldn't find text in output: " + text, systemOutRule.getLog().contains(text));
    }

    protected void checkForMissingString(String text) {
        assertFalse("Found text in output that shouldn't be there: " + text, systemOutRule.getLog().contains(text));
    }

    public int stringCount(String text) {
        String input = systemOutRule.getLog();
        int index = input.indexOf(text);
        int count = 0;
        while (index != -1) {
            count++;
            input = input.substring(index + 1);
            index = input.indexOf(text);
        }
        return count;
    }

}
