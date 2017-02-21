package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    private ArrayList<Book> books = new ArrayList<Book>();

    public BibliotecaApp() {
        initialiseBookList();
    }

    public void start() {
        printWelcome();
        printBooks();
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
        System.out.println("Made my Linus Karsai for TWU.");
        System.out.println("--");
    }

    public void printBooks() {
        for (Book book : books){
           System.out.println(book.toString());
        }
    }

    public static void main(String[] args) {
        BibliotecaApp ba = new BibliotecaApp();
    }
}
