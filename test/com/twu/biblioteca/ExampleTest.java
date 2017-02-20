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

    @Test
    public void checkBooksPrintCorrectly() {
        BibliotecaApp ba = new BibliotecaApp();
        assertTrue(outContent.toString().contains("The Princess Bride"));
        assertTrue(outContent.toString().contains("Hitchhiker's Guide to the Galaxy"));
        assertTrue(outContent.toString().contains("The Princess Bride"));
        assertTrue(outContent.toString().contains("The Sparrow"));
        assertTrue(outContent.toString().contains("Ender's Game"));
        assertTrue(outContent.toString().contains("The Moon is a Harsh Mistress"));
        assertTrue(outContent.toString().contains("The Name of the Wind"));
    }
}
