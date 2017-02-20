package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

public class ExampleTest {

    // used for checking output of System.out.println
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        // reset streams for checking output
        resetStreams();
    }

    @After
    public void cleanUpStreams() {
        // remove streams for checking output
        System.setOut(null);
        System.setErr(null);
    }

    public void resetStreams() {
        // create streams for checking output
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        outContent.reset();
        errContent.reset();
    }
    @Test
    public void checkWelcomeMessage() {
        new BibliotecaApp();
        assertTrue(outContent.toString().contains("Welcome"));
        assertTrue(outContent.toString().contains("Biblioteca"));
    }

    private void checkForBook(String title){
        assertTrue("Couldn't find book in output: " + title, outContent.toString().contains(title));
    }

    @Test
    public void checkBooksPrintCorrectly() {
        BibliotecaApp ba = new BibliotecaApp();
        checkForBook("The Princess Bride");
        checkForBook("Hitchhiker's Guide to the Galaxy");
        checkForBook("The Princess Bride");
        checkForBook("The Sparrow");
        checkForBook("Ender's Game");
        checkForBook("The Moon is a Harsh Mistress");
        checkForBook("The Name of the Wind");
    }

    private void checkForString(String text){
        assertTrue("Couldn't find text in output: " + text, outContent.toString().contains("Patrick Rothfuss"));
    }

    @Test
    public void checkBooksHaveYearAndAuthor() {
        BibliotecaApp ba = new BibliotecaApp();
        checkForString("Patrick Rothfuss");
        checkForString("2007");
        checkForString("Dougla;s Adams");
        checkForString("1979");
    }
}
