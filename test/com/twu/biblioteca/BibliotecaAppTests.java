package com.twu.biblioteca;


import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Suite.SuiteClasses({BookTests.class, MovieTests.class, InterfaceTests.class, UserflowTests.class})

public class BibliotecaAppTests {
}
