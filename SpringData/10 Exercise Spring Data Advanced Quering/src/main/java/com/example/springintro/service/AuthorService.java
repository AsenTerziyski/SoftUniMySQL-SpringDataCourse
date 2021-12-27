package com.example.springintro.service;

import com.example.springintro.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> findAllAuthorsNamesWhoseFirstNameEndsWith(String inputString);

    List<String> findAllAuthorsAndTheirTotalSoldBookCopies();

    Integer findAuthorsTotalBookNumber(String firstName, String lastName);
}
