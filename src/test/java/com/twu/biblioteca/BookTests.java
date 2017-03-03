package com.twu.biblioteca;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by linus on 23/2/17.
 */
public class BookTests extends BaseTest {

    public static Book HITCHHIKERS_GUIDE;
    public static Book PRINCESS_BRIDE;
    public static Book SPARROW;
    public static Book ENDERS_GAME;
    public static Book MOON_MISTRESS;
    public static Book NAME_OF_THE_WIND;
    public static Book HANDMAIDS_TALE;

    protected void initialiseDummyData() {
        // add books
        HITCHHIKERS_GUIDE = new Book("Hitchhiker's Guide to the Galaxy", "Douglas Adams", "1979");
        PRINCESS_BRIDE = new Book("The Princess Bride", "William Goldman", "1973");
        SPARROW = new Book("The Sparrow", "Mary Doria Russell", "1996");
        ENDERS_GAME = new Book("Ender's Game", "Orson Scott Card", "1985");
        MOON_MISTRESS = new Book("The Moon is a Harsh Mistress", "Robert A. Heinlein", "1966");
        NAME_OF_THE_WIND = new Book("The Name of the Wind", "Patrick Rothfuss", "2007");
        HANDMAIDS_TALE = new Book("The Handmaid's Tale", "Margaret Atwood", "1986");
        HANDMAIDS_TALE.checkout(JEAN);

        biblioteca.addRentable(HITCHHIKERS_GUIDE);
        biblioteca.addRentable(PRINCESS_BRIDE);
        biblioteca.addRentable(SPARROW);
        biblioteca.addRentable(ENDERS_GAME);
        biblioteca.addRentable(MOON_MISTRESS);
        biblioteca.addRentable(NAME_OF_THE_WIND);
        biblioteca.addRentable(HANDMAIDS_TALE);
    }

    public void checkForBookTitleText() {
        checkForString(HITCHHIKERS_GUIDE.getTitle());
        checkForString(PRINCESS_BRIDE.getTitle());
        checkForString(SPARROW.getTitle());
        checkForString(ENDERS_GAME.getTitle());
        checkForString(MOON_MISTRESS.getTitle());
        checkForString(NAME_OF_THE_WIND.getTitle());
    }

    private void checkForAuthorAndYearText() {
        checkForString(NAME_OF_THE_WIND.getAuthor());
        checkForString(NAME_OF_THE_WIND.getYearPublished());
        checkForString(HITCHHIKERS_GUIDE.getAuthor());
        checkForString(HITCHHIKERS_GUIDE.getYearPublished());
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
    public void check_print_filters_out_rented_items() {
        int oldOutputLength;
        int newOutputLength;

        biblioteca.printRentables();
        oldOutputLength = output.countLines();
        checkForString(HITCHHIKERS_GUIDE.getTitle());
        HITCHHIKERS_GUIDE.checkout(JEAN);

        output.clear();
        biblioteca.printRentables();
        newOutputLength = output.countLines();
        checkForMissingString(HITCHHIKERS_GUIDE.getTitle());
        assertEquals(oldOutputLength - 1, newOutputLength);
    }

    @Test
    public void check_for_friendly_message_when_checking_out_book() {
        biblioteca.login(JEAN_USERNAME, JEAN_PASS);
        biblioteca.checkout(HITCHHIKERS_GUIDE.getTitle());
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
        checkForMissingString(HANDMAIDS_TALE.getTitle());
        biblioteca.checkin(HANDMAIDS_TALE.getTitle());
        assertFalse(HANDMAIDS_TALE.isCheckedOut());
    }

    @Test
    public void print_number_of_books_hidden() {
        biblioteca.printRentables();
        checkForString("Hiding 1 item(s) because they're checked out.");
    }

    @Test
    public void check_for_message_when_returning_handmaids_tale() {
        biblioteca.checkin(HANDMAIDS_TALE.getTitle());
        checkForString("Thank you for returning the book.");
    }

    @Test
    public void check_for_warning_when_returning_nonexistent_book() {
        biblioteca.checkin("This book doesn't exits...");
        checkForString("That is not a valid item to return.");
    }

//    @Test
//    public void check_that_filter_print_only_prints_books() throws Exception {
//        Movie movie = new Movie("Cool movie", "2017", "Really cool diretctor");
//        biblioteca.addRentable(movie);
//        biblioteca.printRentables(Rentable.RentableType.MOVIE);
//        checkForString("Cool movie");
//    }
}
