package com.twu.biblioteca;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by linus on 23/2/17.
 */
public class BookTests extends BaseTest {
    public static final String HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    public static final String THE_HANDMAID_S_TALE = "The Handmaid's Tale";
    public static final String THE_PRINCESS_BRIDE = "The Princess Bride";
    public static final String THE_SPARROW = "The Sparrow";
    public static final String ENDER_S_GAME = "Ender's Game";
    public static final String THE_MOON_IS_A_HARSH_MISTRESS = "The Moon is a Harsh Mistress";
    public static final String THE_NAME_OF_THE_WIND = "The Name of the Wind";


    protected void initialiseDummyData() {
        // add books
        biblioteca.addRentable(new Book(HITCHHIKER_S_GUIDE_TO_THE_GALAXY, "Douglas Adams", "1979"));
        biblioteca.addRentable(new Book(THE_PRINCESS_BRIDE, "William Goldman", "1973"));
        biblioteca.addRentable(new Book(THE_SPARROW, "Mary Doria Russell", "1996"));
        biblioteca.addRentable(new Book(ENDER_S_GAME, "Orson Scott Card", "1985"));
        biblioteca.addRentable(new Book(THE_MOON_IS_A_HARSH_MISTRESS, "Robert A. Heinlein", "1966"));
        biblioteca.addRentable(new Book(THE_NAME_OF_THE_WIND, "Patrick Rothfuss", "2007"));

        // create book that's checked out
        Book handmaidsTale = new Book(THE_HANDMAID_S_TALE, "Margaret Atwood", "1986");
        handmaidsTale.checkout();
        biblioteca.addRentable(handmaidsTale);
    }

    public void checkForBookTitleText() {
        checkForString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForString(THE_PRINCESS_BRIDE);
        checkForString(THE_SPARROW);
        checkForString(ENDER_S_GAME);
        checkForString(THE_MOON_IS_A_HARSH_MISTRESS);
        checkForString(THE_NAME_OF_THE_WIND);
    }

    private void checkForAuthorAndYearText() {
        checkForString("Patrick Rothfuss");
        checkForString("2007");
        checkForString("Douglas Adams");
        checkForString("1979");
    }

    @Test
    public void check_books_titles_are_printed() {
        biblioteca.printRentables();
        checkForBookTitleText();
    }

    @Test
    public void check_books_have_author_and_year() {
        biblioteca.printRentables();
        checkForAuthorAndYearText();
    }

    @Test
    public void checkout_hitchhikers_guide() {
        int oldOutputLength;
        int newOutputLength;

        biblioteca.printRentables();
        oldOutputLength = systemOutRule.getLog().split("\n").length;
        checkForString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);

        systemOutRule.clearLog();
        biblioteca.printRentables();
        newOutputLength = systemOutRule.getLog().split("\n").length;
        checkForMissingString(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        assertEquals(oldOutputLength - 1, newOutputLength);
    }

    @Test
    public void check_for_friendly_message_when_checking_out_book() {
        biblioteca.checkout(HITCHHIKER_S_GUIDE_TO_THE_GALAXY);
        checkForString("Thank you!");
        checkForString("Enjoy the book");
    }

    @Test
    public void check_for_warning_when_checking_out_nonexistent_book() {
        biblioteca.checkout("Book that doesn't exist...");
        checkForString("That item is not available.");
    }

    @Test
    public void return_book_handmaids_tale() {
        checkForMissingString(THE_HANDMAID_S_TALE);

        // return book
        systemOutRule.clearLog();
        biblioteca.checkin(THE_HANDMAID_S_TALE);
        biblioteca.printRentables();
        checkForString(THE_HANDMAID_S_TALE);
    }

    @Test
    public void print_number_of_books_hidden() {
        biblioteca.printRentables();
        checkForString("Hiding 1 item(s) because they're checked out.");
    }

    @Test
    public void check_for_message_when_returning_handmaids_tale() {
        biblioteca.checkin(THE_HANDMAID_S_TALE);
        checkForString("Thank you for returning the book.");
    }

    @Test
    public void check_for_warning_when_returning_nonexistent_book() {
        biblioteca.checkin("This book doesn't exits...");
        checkForString("That is not a valid item to return.");
    }


}
