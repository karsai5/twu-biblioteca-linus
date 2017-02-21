package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class BibliotecaAppTests {

    // used for checking output of System.out.println
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        // reset streams for checking output
        clearSystemOutBuffers();
    }

    @After
    public void cleanUpStreams() {
        // remove streams for checking output
        System.setOut(null);
        System.setErr(null);
    }

    public void clearSystemOutBuffers() {
        // create streams for checking output
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        outContent.reset();
        errContent.reset();
    }
    @Test
    public void check_welcome_message_is_printed() {
        new BibliotecaApp();
        assertTrue(outContent.toString().contains("Welcome"));
        assertTrue(outContent.toString().contains("Biblioteca"));
    }

    private void checkForBook(String title){
        assertTrue("Couldn't find book in output: " + title, outContent.toString().contains(title));
    }

    @Test
    public void check_books_titles_are_printed() {
        BibliotecaApp ba = new BibliotecaApp();
        clearSystemOutBuffers();
        ba.printBooks();
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
    public void check_books_have_author_and_year() {
        BibliotecaApp ba = new BibliotecaApp();
        clearSystemOutBuffers();
        ba.printBooks();
        checkForString("Patrick Rothfuss");
        checkForString("2007");
        checkForString("Dougla;s Adams");
        checkForString("1979");
    }
}
