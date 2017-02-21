package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private ArrayList<Book> books = new ArrayList<Book>();

    public BibliotecaApp() {
        initialiseBookList();
    }

    private void printInvalidMenuOption() {
        System.out.println("Select a valid option!");
    }

    public void start() {
        printWelcome();
        while(true){
            printMenu();
            int n = -1; // Scans the next token of the input as an int.
            try {
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                System.out.println("Enter a number: ");
                n = reader.nextInt();
                if(n == 1) {
                    printBooks();
                } else if (n==2) {
                    break;
                } else {
                    printInvalidMenuOption();
                }
            } catch (Exception e) {
                printInvalidMenuOption();
            }

        }
    }

    private void initialiseBookList() {
        books.add(new Book("Hitchhiker's Guide to the Galaxy", "Douglas Adams","1979"));
        books.add(new Book("The Princess Bride", "William Goldman", "1973"));
        books.add(new Book("The Sparrow", "Mary Doria Russell", "1996"));
        books.add(new Book("Ender's Game", "Orson Scott Card", "1985"));
        books.add(new Book("The Moon is a Harsh Mistress", "Robert A. Heinlein", "1966"));
        books.add(new Book("The Name of the Wind", "Patrick Rothfuss", "2007"));
    }

    public void printWelcome() {
        System.out.println("Welcome to Biblioteca, the home of books.");
        System.out.println("Made by Linus Karsai for TWU.");
        System.out.println("--");
    }

    public void printBooks() {
        for (Book book : books){
            if (!book.isCheckedOut())
                System.out.println(book.toString());
        }
    }

    public static void main(String[] args) {
        BibliotecaApp ba = new BibliotecaApp();
        ba.start();
    }

    public void printMenu() {
        System.out.println("Main Menu");
        System.out.println("=========");
        System.out.println("1. List Books");
        System.out.println("2. Quit");
    }

    public void checkout(String title) {
        for (Book book : books){
            if (book.getTitle().equals(title))
                book.checkout();
        }
    }
}
