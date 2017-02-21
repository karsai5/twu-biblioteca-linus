package com.twu.biblioteca;


import org.junit.*;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.*;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class BibliotecaAppTests {

    BibliotecaApp biblioteca;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();


    @Before
    public void setUpStreams() throws IOException {
        biblioteca = new BibliotecaApp();
        systemOutRule.clearLog();
    }

    @Test
    public void check_welcome_message() {
        biblioteca.printWelcome();
        assertTrue(systemOutRule.getLog().contains("Welcome"));
        assertTrue(systemOutRule.getLog().contains("Biblioteca"));
    }

    private void checkForBook(String title){
        assertTrue("Couldn't find book in output: " + title, systemOutRule.getLog().contains(title));
    }

    @Test
    public void check_books_titles_are_printed() {
        BibliotecaApp ba = new BibliotecaApp();
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
        assertTrue("Couldn't find text in output: " + text, systemOutRule.getLog().contains(text));
    }

    @Test
    public void check_books_have_author_and_year() {
        BibliotecaApp ba = new BibliotecaApp();
        ba.printBooks();
        checkForString("Patrick Rothfuss");
        checkForString("2007");
        checkForString("Douglas Adams");
        checkForString("1979");
    }

    @Test
    public void check_menu_prints() throws Exception {
        BibliotecaApp ba  = new BibliotecaApp();
        ba.printMenu();
        checkForString("Main Menu");
        checkForString("List Books");
    }

    @Ignore
    @Test(timeout = 2000)
    public void check_userflow_viewing_book_list() throws Exception {
        String simulatedUserInput = "1" + System.getProperty("line.separator")
                + "2" + System.getProperty("line.separator");

        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        BibliotecaApp ba  = new BibliotecaApp();
        ba.start();
        checkForString("Hitchhiker's Guide to the Galaxy");

        System.setIn(savedStandardInputStream);
    }

    @Test
    public void overrideProperty() {
        System.out.print("hello world");
        assertEquals("hello world", systemOutRule.getLog());
    }
}
