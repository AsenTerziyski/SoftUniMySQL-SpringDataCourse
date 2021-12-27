package com.example.exercisesdi;

public class InputFilesPaths {
    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";
    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";
    private static final String CATEGORIES_FILE_PATH = "src/main/resources/files/categories.txt";

    public static String getAuthorsFilePath() {
        return AUTHORS_FILE_PATH;
    }

    public static String getBooksFilePath() {
        return BOOKS_FILE_PATH;
    }

    public static String getCategoriesFilePath() {
        return CATEGORIES_FILE_PATH;
    }
}
