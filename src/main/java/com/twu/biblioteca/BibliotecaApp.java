package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    public static class Input {
        public String getInput(){
            return getInput("");
        }
        public String getInput(String message) {
            Scanner reader = new Scanner(System.in);
            System.out.println(message);
            return reader.nextLine();
        }
    }

    public static class Output {
        public void printOutput(String message) {
            System.out.println(message);
        }
    }

    private static final int MENU_OPTION_LIST_BOOKS = 1;
    private static final int MENU_CHECKOUT_BOOK = 2;
    private static final int MENU_RETURN_BOOK = 3;
    private static final int MENU_OPTION_QUIT = 4;

    private ArrayList<Rentable> rentables = new ArrayList<Rentable>();
    private ArrayList<User> users = new ArrayList<User>();
    private User currentUser = null;

    private Input input;
    private Output output;

    public void setInput(Input input) {
        this.input = input;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public BibliotecaApp() {
        BibliotecaExampleData.initialiseBookList(this);

        this.output = new Output();
        this.input = new Input();
    }

    private int getMenuOption() {
        String menuOption = input.getInput("Enter a number: ");
        try {
            return Integer.parseInt(menuOption);
        } catch (Exception e) {
            return -1;
        }
    }

    public void startInteractiveShell() {
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

            switch (menuNumber) {
                case MENU_OPTION_LIST_BOOKS:
                    printRentables();
                    break;
                case MENU_CHECKOUT_BOOK:
                    checkoutRentableInteractively();
                    break;
                case MENU_OPTION_QUIT:
                    running = false;
                    break;
                case MENU_RETURN_BOOK:
                    returnRentableInteractively();
                    break;
                default:
                    output.printOutput("Select a valid option!");

            }
        }
    }

    private String getInputFromUser(String message) {
        return input.getInput(message);
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
        output.printOutput("Welcome to Biblioteca, the home of books (and other things).");
        output.printOutput("Made by Linus Karsai for TWU.");
        output.printOutput("--");
    }

    public void printRentables() {
        int numOfCheckedOutItems = 0;

        for (Rentable rentable : rentables) {
                if (!rentable.isCheckedOut()) {
                    output.printOutput(rentable.toString());
                } else {
                    ++numOfCheckedOutItems;
                }
        }

        if (numOfCheckedOutItems > 0)
            output.printOutput(String.format("Hiding %d item(s) because they're checked out.\n", numOfCheckedOutItems));
    }

    public void printRentables(Rentable.RentableType rentableType) {
        int numOfCheckedOutItems = 0;

        for (Rentable rentable : rentables) {
            if (rentable.isRentableType(rentableType)) { // check it is part of filter class
                if (!rentable.isCheckedOut()) {
                    output.printOutput(rentable.toString());
                } else {
                    ++numOfCheckedOutItems;
                }
            }
        }

        if (numOfCheckedOutItems > 0)
            output.printOutput(String.format("Hiding %d item(s) because they're checked out.\n", numOfCheckedOutItems));
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
        ba.startInteractiveShell();
    }

    public void printMenu() {
        output.printOutput("Main Menu");
        output.printOutput("=========");
        output.printOutput("1. Print catalogue");
        output.printOutput("2. Checkout item");
        output.printOutput("3. Return item");
        output.printOutput("4. Quit");
    }

    public void checkout(String title) {
        Rentable itemToCheckout = findRentable(title);
        if (itemToCheckout != null) {
            itemToCheckout.checkout(currentUser);
            output.printOutput(String.format("Thank you! Enjoy the %s.\n", itemToCheckout.getReadableName()));
        } else {
            output.printOutput("That item is not available.");
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
            output.printOutput(String.format("Thank you for returning the %s.\n", rentableToReturn.getReadableName()));
            rentableToReturn.checkin();
        } else {
            output.printOutput("That is not a valid item to return.");
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
            output.printOutput("Username and/or password incorrect.");
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
            output.printOutput(String.format("Library Number: %s\n", currentUser.getUsername()));
            output.printOutput(String.format("Name: %s\n", currentUser.getName()));
            output.printOutput(String.format("Email: %s\n", currentUser.getEmail()));
            output.printOutput(String.format("Phone: %s\n", currentUser.getPhone()));
        }
    }
}
