package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Rule;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by linus on 23/2/17.
 */
public abstract class BaseTest {
    protected BibliotecaApp biblioteca;
    protected User JEAN;
    protected String JEAN_USERNAME = "222-4601";
    protected String JEAN_PASS = "password";
    protected String JEAN_NAME = "Jean Valjean";
    protected String JEAN_EMAIL = "jean@lesmiserables.com";
    protected String JEAN_PHONE = "04 1234 1234";

    protected MockInput input;
    protected MockOutput output;

    protected abstract void initialiseDummyData();

    public class MockInput extends BibliotecaApp.Input {
        // Overrides the default Input used in BibliotecaApp
        // so that multiple commands can be added and will be
        // presented to the application on getInput()
        LinkedList<String> commands = new LinkedList<String>();

        public void addCommand(String command) {
            commands.addLast(command);
        }

        @Override
        public String getInput(String message) {
            return commands.pop();
        }

        @Override
        public String getInput() {
            return commands.pop();
        }
    }

    public class MockOutput extends BibliotecaApp.Output {
        // Overrides the default Output used in BibliotecaApp
        // Output is stored in an array list that can then be searched
        // and counted
        public ArrayList<String> buffer = new ArrayList<String>();

        @Override
        public void printOutput(String message) {
            buffer.add(message);
        }

        public boolean contains(String text) {
            for (String s : buffer) {
                if (s.contains(text)) {
                    return true;
                }
            }
            return false;
        }

        public void clear() {
            buffer.clear();
        }

        public int countLines() {
            return buffer.size();
        }
    }

    @Before
    public void setUpStreams() {
        biblioteca = new BibliotecaApp();
        biblioteca.clearRentables();
        biblioteca.addUser(JEAN = new User(JEAN_USERNAME, JEAN_PASS, JEAN_NAME, JEAN_EMAIL, JEAN_PHONE));

        input = new MockInput();
        output = new MockOutput();
        biblioteca.setInput(input);
        biblioteca.setOutput(output);

        initialiseDummyData();
    }

    protected void checkForString(String text) {
        assertTrue("Couldn't find text in output: " + text, output.contains(text));
    }

    protected void checkForMissingString(String text) {
        assertFalse("Found text in output that shouldn't be there: " + text, output.contains(text));
    }

    private int stringCount(String text, String lookingFor) {
        int index = text.indexOf(lookingFor);
        int count = 0;
        while (index != -1) {
            count++;
            text = text.substring(index + 1);
            index = text.indexOf(lookingFor);
        }
        return count;
    }


    public int stringCount(String text) {
        int count = 0;
        for (String s : output.buffer) {
            count += stringCount(s, text);
        }
        return count;
    }

}
