package com.example.exercisesdi.service;

import com.example.exercisesdi.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> findAllBooksAfterYear (int year);

    List<String> findAllAuthorsNamesOfBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorsFirstAndLastNamesOrderedByReleaseDate(String firstName, String lastName);
}
