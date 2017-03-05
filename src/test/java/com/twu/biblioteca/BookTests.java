package com.twu.biblioteca;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * Created by linus on 23/2/17.
 */
public class BookTests extends BaseTest {

    protected void initialiseDummyData() {
    }

    @Test
    public void check_hitchhikers_title_is_printed() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        biblioteca.addRentable(hitchhikersGuide);

        biblioteca.printRentables();
        checkForString("The Hitchhikers Guide");
    }

    @Test
    public void check_hitchhikers_guide_author_and_year_are_printed() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        biblioteca.addRentable(hitchhikersGuide);

        biblioteca.printRentables();
        checkForString("Douglas Adams");
        checkForString("1979");
    }

    @Test
    public void check_print_filters_out_rented_items() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        hitchhikersGuide.checkout(mock(User.class));
        biblioteca.addRentable(hitchhikersGuide);

        biblioteca.printRentables();
        checkForString("Hiding 1 item");
        checkForMissingString("The Hitchhikers Guide");
    }

    @Test
    public void check_for_friendly_message_when_checking_out_book() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        biblioteca.addRentable(hitchhikersGuide);
        biblioteca.login(JEAN_USERNAME, JEAN_PASS);

        biblioteca.checkout("The Hitchhikers Guide");

        checkForString("Thank you!");
        checkForString("Enjoy the book");
    }

    @Test
    public void check_for_warning_when_checking_out_nonexistent_book() {
        biblioteca.checkout("");
        checkForString("That item is not available.");
    }

    @Test
    public void return_book_via_bilioteca() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        hitchhikersGuide.checkout(mock(User.class));
        biblioteca.addRentable(hitchhikersGuide);

        biblioteca.checkin("The Hitchhikers Guide");
        assertFalse(hitchhikersGuide.isCheckedOut());
    }

    @Test
    public void check_for_message_when_returning_book() {
        biblioteca.addRentable(new Book("The Hitchhikers Guide", "Douglas Adams", "1979"));

        biblioteca.checkin("The Hitchhikers Guide");
        checkForString("Thank you for returning the book.");
    }


    @Test
    public void print_number_of_books_hidden() {
        Book hitchhikersGuide = new Book("The Hitchhikers Guide", "Douglas Adams", "1979");
        hitchhikersGuide.checkout(mock(User.class));
        biblioteca.addRentable(hitchhikersGuide);
        biblioteca.addRentable(new Book("The Handmaids Tale", "Margaret Atwood", "1986"));

        biblioteca.printRentables();
        checkForString("Hiding 1 item");
    }

    @Test
    public void check_for_warning_when_returning_nonexistent_book() {
        biblioteca.checkin("");
        checkForString("That is not a valid item to return.");
    }
}
