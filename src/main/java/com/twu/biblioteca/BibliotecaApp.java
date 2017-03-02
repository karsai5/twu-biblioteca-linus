package com.twu.biblioteca;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class BibliotecaApp {

    private class MenuOption {
       String name;
       Callable<Void> function;

        public MenuOption(String name, Callable<Void> function) {
            this.name = name;
            this.function = function;
        }
        public void run() {
            try {
                function.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private static final int MENU_OPTION_LIST_BOOKS = 1;
    private static final int MENU_CHECKOUT_BOOK = 2;
    private static final int MENU_RETURN_BOOK = 3;
    private static final int MENU_OPTION_QUIT = 4;

    private ArrayList<Rentable> rentables = new ArrayList<Rentable>();
    private ArrayList<User> users = new ArrayList<User>();
    private User currentUser = null;
    private HashMap<Integer, MenuOption> menuOptions = new HashMap<Integer, MenuOption>();

    public BibliotecaApp() {
        BibliotecaExampleData.initialiseBookList(this);
        createMenu();
    }

    private void createMenu() {
        menuOptions.put(1, new MenuOption("Print catalogue", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        printRentables();
                        return null;
                    }
                }));
        menuOptions.put(2, new MenuOption("Checkout book", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                checkoutRentableInteractively();
                return null;
            }
        }));
        menuOptions.put(3, new MenuOption("Return book", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                returnRentableInteractively();
                return null;
            }
        }));
        menuOptions.put(4, new MenuOption("Quit", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                throw new Exception("End of application.");
            }
        }));
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
        if (currentUser == null) {
            startInteractiveLogin();
        }
        if (currentUser != null) {
            startInteractiveMenu();
        }
    }

    private void startInteractiveLogin() {
        String username = getInputFromUser("Enter username:");
        String password = getInputFromUser("Enter password:");
        login(username, password);
    }

    private void startInteractiveMenu() {
        // start menu loop
        boolean running = true;
        while (running) {
            printMenu();
            int menuNumber = getMenuOption();

            if (menuOptions.containsKey(menuNumber)) {
                try {
                    menuOptions.get(menuNumber).run();
                } catch (Exception e){
                    running = false;
                }
            } else {
                printInvalidMenuOption();
            }
        }
    }

    private String getInputFromUser(String message) {
        Scanner reader = new Scanner(System.in);
        System.out.println(message);
        return reader.nextLine();
    }

    private void returnRentableInteractively() {
        String title = getInputFromUser("Enter the name of the item you want to return:");
        checkin(title);
    }

    private void checkoutRentableInteractively() {
        String title = getInputFromUser("Enter the name of the item you want to checkout:");
        checkout(title);
    }

    public void printWelcome() {
        System.out.println("Welcome to Biblioteca, the home of books (and other things).");
        System.out.println("Made by Linus Karsai for TWU.");
        System.out.println("--");
    }

    public void printRentables() {
        int numOfCheckedOutItems = 0;

        for (Rentable rentable : rentables) {
                if (!rentable.isCheckedOut()) {
                    System.out.println(rentable.toString());
                } else {
                    ++numOfCheckedOutItems;
                }
        }

        if (numOfCheckedOutItems > 0)
            System.out.printf("Hiding %d item(s) because they're checked out.\n", numOfCheckedOutItems);
    }

    public void printRentables(Rentable.RentableType rentableType) {
        int numOfCheckedOutItems = 0;

        for (Rentable rentable : rentables) {
            if (rentable.isRentableType(rentableType)) { // check it is part of filter class
                if (!rentable.isCheckedOut()) {
                    System.out.println(rentable.toString());
                } else {
                    ++numOfCheckedOutItems;
                }
            }
        }

        if (numOfCheckedOutItems > 0)
            System.out.printf("Hiding %d item(s) because they're checked out.\n", numOfCheckedOutItems);
    }

    public Rentable findRentable(String title) {
        for (Rentable rentable : rentables) {
            if (rentable.getTitle().equals(title)) {
                return rentable;
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

        for (Map.Entry<Integer, MenuOption> menuEntry : menuOptions.entrySet()) {
            System.out.println(menuEntry.getKey() + ". " + menuEntry.getValue().name);
        }
    }

    public void checkout(String title) {
        Rentable itemToCheckout = findRentable(title);
        if (itemToCheckout != null) {
            itemToCheckout.checkout(currentUser);
            System.out.printf("Thank you! Enjoy the %s.\n", itemToCheckout.getReadableName());
        } else {
            System.out.println("That item is not available.");
        }
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void clearRentables() {
        rentables.clear();
    }

    public void checkin(String title) {
        Rentable rentableToReturn = findRentable(title);
        if (rentableToReturn != null) {
            System.out.printf("Thank you for returning the %s.\n", rentableToReturn.getReadableName());
            rentableToReturn.checkin();
        } else {
            System.out.println("That is not a valid item to return.");
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                currentUser = user;
            }
        }
        if (currentUser == null) {
            System.out.println("Username and/or password incorrect.");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logOut() {
        currentUser = null;
    }

    public void printCurrentUsersDetails() {
        if (currentUser != null) {
            System.out.printf("Library Number: %s\n", currentUser.getUsername());
            System.out.printf("Name: %s\n", currentUser.getName());
            System.out.printf("Email: %s\n", currentUser.getEmail());
            System.out.printf("Phone: %s\n", currentUser.getPhone());
        }
    }
}
