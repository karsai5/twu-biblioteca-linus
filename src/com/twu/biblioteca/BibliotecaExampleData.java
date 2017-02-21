package com.twu.biblioteca;

/**
 * Created by linus on 22/2/17.
 */
public class BibliotecaExampleData {

    // I wasn't comfortable having all the example data in the main applicaiton class. So it is instead stored
    // in this external class.

    public static final String HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    public static final String THE_HANDMAID_S_TALE = "The Handmaid's Tale";
    public static final String THE_PRINCESS_BRIDE = "The Princess Bride";
    public static final String THE_SPARROW = "The Sparrow";
    public static final String ENDER_S_GAME = "Ender's Game";
    public static final String THE_MOON_IS_A_HARSH_MISTRESS = "The Moon is a Harsh Mistress";
    public static final String THE_NAME_OF_THE_WIND = "The Name of the Wind";

    public static void initialiseBookList(BibliotecaApp bibliotecaApp) {
        bibliotecaApp.addBook(new Book(HITCHHIKER_S_GUIDE_TO_THE_GALAXY, "Douglas Adams", "1979"));
        bibliotecaApp.addBook(new Book(THE_PRINCESS_BRIDE, "William Goldman", "1973"));
        bibliotecaApp.addBook(new Book(THE_SPARROW, "Mary Doria Russell", "1996"));
        bibliotecaApp.addBook(new Book(ENDER_S_GAME, "Orson Scott Card", "1985"));
        bibliotecaApp.addBook(new Book(THE_MOON_IS_A_HARSH_MISTRESS, "Robert A. Heinlein", "1966"));
        bibliotecaApp.addBook(new Book(THE_NAME_OF_THE_WIND, "Patrick Rothfuss", "2007"));

        // create book that's checked out
        Book handmaidsTale = new Book(THE_HANDMAID_S_TALE, "Margaret Atwood", "1986");
        handmaidsTale.checkout();
        bibliotecaApp.addBook(handmaidsTale);
    }

}
