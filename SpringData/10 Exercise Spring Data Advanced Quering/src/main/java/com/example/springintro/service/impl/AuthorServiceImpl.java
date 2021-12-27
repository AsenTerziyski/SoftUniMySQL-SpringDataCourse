package com.example.springintro.service.impl;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.repository.AuthorRepository;
import com.example.springintro.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (authorRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row ->
                {
                    String[] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0], fullName[1]);
                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1,
                        authorRepository.count() + 1);
        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthorsNamesWhoseFirstNameEndsWith(String inputString) {
        return this.authorRepository.findAllByFirstNameEndingWith(inputString)
                .stream().map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthorsAndTheirTotalSoldBookCopies() {
        List<Author> allAuthors = this.authorRepository
                .findAll();
        List<String> collect = allAuthors
                .stream()
                .map(a ->
        {
            Integer sumCopies = a.getBooks()
                    .stream()
                    .map(Book::getCopies)
                    .reduce(Integer::sum)
                    .orElse(0);
            return String.format("%s %s - %d", a.getFirstName(), a.getLastName(), sumCopies);
        })
                .collect(Collectors.toList());
        Map<Integer, String> sorted = new LinkedHashMap<>();
        for (String s : collect) {
            int i = Integer.parseInt(s.split(" - ")[1]);
            sorted.put(i, s);
        }
        List<String> sortedResult = sorted
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getKey().compareTo(a.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return sortedResult;
    }

    @Override
    public Integer findAuthorsTotalBookNumber(String firstName, String lastName) {
        Integer totalBookNumberOfAuthorByFirstAndLastName = this.authorRepository.findTotalBookNumberOfAuthorByFirstAndLastName(firstName, lastName);
        System.out.println();
        return null;

    }
}
