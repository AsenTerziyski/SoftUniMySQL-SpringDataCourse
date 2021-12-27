package com.example.exercisesdi.service.impl;


import com.example.exercisesdi.model.entity.Author;
import com.example.exercisesdi.repository.AuthorRepository;
import com.example.exercisesdi.service.AuthorService;
import com.example.exercisesdi.InputFilesPaths;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
//    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() > 0) {
            return;
        }

        String authorsFilePath = InputFilesPaths.getAuthorsFilePath();

        Files.readAllLines(Path.of(authorsFilePath)).forEach(row -> {
            String[] fullName = row.split("\\s+");
            String firstName = fullName[0];
            String lastName = fullName[1];
            Author author = new Author(firstName, lastName);
            this.authorRepository.save(author);
        });

    }

    @Override
    public Author getRandomAuthor() {

        Long randomId = ThreadLocalRandom
                .current()
                .nextLong(1, authorRepository.count() + 1);

        Author author = authorRepository
                .findById(randomId)
                .orElse(null);
        return author;
    }

    @Override
    public List<String> getAllAuthorsByCountOfTheirBooks() {
        List<String> authorsNamesAndTheirBooksCount =
                authorRepository
                        .findAllByBooksCountSizeDesc()
                        .stream()
                        .map(a -> String.format("%s %s %d", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                        .collect(Collectors.toList());
        return authorsNamesAndTheirBooksCount;
    }
}
