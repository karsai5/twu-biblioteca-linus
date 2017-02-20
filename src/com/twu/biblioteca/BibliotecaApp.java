package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    private ArrayList<String> books = new ArrayList<String>();

    public BibliotecaApp() {
        this.start();
    }

    private void initialiseBookList() {
        books.add("Hitchhiker's Guide to the Galaxy");
        books.add("The Princess Bride");
        books.add("The Sparrow");
        books.add("Ender's Game");
        books.add("The Moon is a Harsh Mistress");
        books.add("The Name of the Wind");
    }

    public void printWelcome() {
        System.out.println("Welcome to Biblioteca, the home of books.");
        System.out.println("Made my Linus Karsai for TWU.");
        System.out.println("--");
    }

    public void start() {
        initialiseBookList();
        printWelcome();
        printBooks();
    }

    public void printBooks() {
        for (String book : books){
           System.out.println(book);
        }
    }

    public static void main(String[] args) {
        BibliotecaApp ba = new BibliotecaApp();
        ba.start();
        ba.printBooks();
    }
}
