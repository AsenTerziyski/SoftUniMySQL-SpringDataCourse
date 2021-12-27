package com.example.exercisesdi.service;

import com.example.exercisesdi.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;


    Author getRandomAuthor();

    List<String> getAllAuthorsByCountOfTheirBooks();

}
