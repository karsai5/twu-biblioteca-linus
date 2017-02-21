package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private ArrayList<Book> books = new ArrayList<Book>();

    public BibliotecaApp() {
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
                case 1:
                    printBooks();
                    break;
                case 2:
                    checkoutBookInteractively();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    printInvalidMenuOption();

            }
        }
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
        for (Book book : books) {
            if (!book.isCheckedOut())
                System.out.println(book.toString());
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
        System.out.println("3. Quit");
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

    public void returnBook(String title) {
        Book bookToReturn = findBook(title);
        if (bookToReturn != null) {
            bookToReturn.returnBook();
        }
    }
}
