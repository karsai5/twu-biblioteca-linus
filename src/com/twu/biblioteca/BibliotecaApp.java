package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private static final int MENU_OPTION_LIST_BOOKS = 1;
    private static final int MENU_CHECKOUT_BOOK = 2;
    private static final int MENU_RETURN_BOOK = 3;
    private static final int MENU_OPTION_QUIT = 4;

    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public BibliotecaApp() {
        BibliotecaExampleData.initialiseBookList(this);
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
        boolean running = true;
        while (running) {
            printMenu();
            int menuNumber = getMenuOption();

            switch (menuNumber) {
                case MENU_OPTION_LIST_BOOKS:
                    printCatalogue();
                    break;
                case MENU_CHECKOUT_BOOK:
                    checkoutBookInteractively();
                    break;
                case MENU_OPTION_QUIT:
                    running = false;
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

    public void printCatalogue() {
        int numOfCheckedOutBooks = 0;

        System.out.println("Books:");
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                System.out.println(book.toString());
            } else {
                ++numOfCheckedOutBooks;
            }
        }

        System.out.println("Movies:");
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
        if (numOfCheckedOutBooks > 0)
            System.out.printf("Hiding %d book(s) because they're checked out.\n", numOfCheckedOutBooks);
    }

    public void printMovies() {
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
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

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void clearBooks() {
        books.clear();
    }

    public void returnBook(String title) {
        Book bookToReturn = findBook(title);
        if (bookToReturn != null) {
            System.out.println("Thank you for returning the book.");
            bookToReturn.returnBook();
        } else {
            System.out.println("That is not a valid book to return.");
        }
    }
}
