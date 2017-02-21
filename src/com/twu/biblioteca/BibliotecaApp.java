package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private static final int MENU_OPTION_LIST_BOOKS = 1;
    private static final int MENU_CHECKOUT_BOOK = 2;
    private static final int MENU_RETURN_BOOK = 3;
    private static final int MENU_OPTION_QUIT = 4;

    public static final String HITCHHIKER_S_GUIDE_TO_THE_GALAXY = "Hitchhiker's Guide to the Galaxy";
    public static final String THE_HANDMAID_S_TALE = "The Handmaid's Tale";
    public static final String THE_PRINCESS_BRIDE = "The Princess Bride";
    public static final String THE_SPARROW = "The Sparrow";
    public static final String ENDER_S_GAME = "Ender's Game";
    public static final String THE_MOON_IS_A_HARSH_MISTRESS = "The Moon is a Harsh Mistress";
    public static final String THE_NAME_OF_THE_WIND = "The Name of the Wind";

    private ArrayList<Book> books = new ArrayList<Book>();

    public BibliotecaApp() {
        initialiseBookList();
    }

    private void initialiseBookList() {
        addBook(new Book(HITCHHIKER_S_GUIDE_TO_THE_GALAXY, "Douglas Adams", "1979"));
        addBook(new Book(THE_PRINCESS_BRIDE, "William Goldman", "1973"));
        addBook(new Book(THE_SPARROW, "Mary Doria Russell", "1996"));
        addBook(new Book(ENDER_S_GAME, "Orson Scott Card", "1985"));
        addBook(new Book(THE_MOON_IS_A_HARSH_MISTRESS, "Robert A. Heinlein", "1966"));
        addBook(new Book(THE_NAME_OF_THE_WIND, "Patrick Rothfuss", "2007"));

        // create book that's checked out
        Book handmaidsTale = new Book(THE_HANDMAID_S_TALE, "Margaret Atwood", "1986");
        handmaidsTale.checkout();
        addBook(handmaidsTale);
    }

    private void printInvalidMenuOption() {
        System.out.println("Select a valid option!");
    }

    private int getMenuOption() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        try {
            return Integer.parseInt(reader.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public void start() {
        printWelcome();
        startInteractiveMenu();
    }

    private void startInteractiveMenu() {
        // start menu loop
        while (true) {
            printMenu();
            int menuNumber = getMenuOption();

            switch (menuNumber) {
                case MENU_OPTION_LIST_BOOKS:
                    printBooks();
                    break;
                case MENU_CHECKOUT_BOOK:
                    checkoutBookInteractively();
                    break;
                case MENU_OPTION_QUIT:
                    System.exit(0);
                    break;
                case MENU_RETURN_BOOK:
                    returnBookInteractively();
                    break;
                default:
                    printInvalidMenuOption();

            }
        }
    }

    private void returnBookInteractively() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the name of the book you want to return:");
        returnBook(reader.nextLine());
    }

    private void checkoutBookInteractively() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the name of the book you want to checkout:");
        checkout(reader.nextLine());
    }

    public void printWelcome() {
        System.out.println("Welcome to Biblioteca, the home of books.");
        System.out.println("Made by Linus Karsai for TWU.");
        System.out.println("--");
    }

    public void printBooks() {
        int numOfCheckedOutBooks = 0;
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                System.out.println(book.toString());
            } else {
                ++numOfCheckedOutBooks;
            }
        }
        if (numOfCheckedOutBooks > 0)
            System.out.printf("Hiding %d book(s) because they're checked out.\n", numOfCheckedOutBooks);
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BibliotecaApp ba = new BibliotecaApp();
        ba.start();
    }

    public void printMenu() {
        System.out.println("Main Menu");
        System.out.println("=========");
        System.out.println("1. List Books");
        System.out.println("2. Checkout Book");
        System.out.println("3. Return Book");
        System.out.println("4. Quit");
    }

    public void checkout(String title) {
        Book bookToCheckout = findBook(title);
        if (bookToCheckout != null) {
            bookToCheckout.checkout();
            System.out.println("Thank you! Enjoy the book.");
        } else {
            System.out.println("That book is not available.");
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void clearBooks() {
        books.clear();
    }


    public void returnBook(String title) {
        Book bookToReturn = findBook(title);
        if (bookToReturn != null) {
            bookToReturn.returnBook();
        }
    }
}
