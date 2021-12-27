package com.example.exercisesdi.service.impl;

import com.example.exercisesdi.model.entity.*;
import com.example.exercisesdi.repository.BookRepository;
import com.example.exercisesdi.service.AuthorService;
import com.example.exercisesdi.service.BookService;
import com.example.exercisesdi.service.CategoryService;
import com.example.exercisesdi.InputFilesPaths;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
//    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }
        //Files.readAllLines returns Strings:
        String booksFilePath = InputFilesPaths.getBooksFilePath();
        List<String> booksInfo = Files.readAllLines(Path.of(booksFilePath));

        booksInfo.forEach(eachBook -> {
            String[] eachBookInfo = eachBook.split("\\s+");
            Book currentBook = createBookFromInputBookInfo(eachBookInfo);
            bookRepository.save(currentBook);
        });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {

        List<Book> allByReleaseDateAfter = bookRepository.findAllByReleaseDateAfter(LocalDate.of(year - 1, 12, 31));
        return allByReleaseDateAfter;
    }

    @Override
    public List<String> findAllAuthorsNamesOfBooksWithReleaseDateBeforeYear(int year) {
        List<String> authorNames = bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream().map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct().collect(Collectors.toList());
        return authorNames;
    }

    @Override
    public List<String> findAllBooksByAuthorsFirstAndLastNamesOrderedByReleaseDate(String firstName, String lastName) {
//        List<Book> allByAuthor_firstNameAndAuthor_lastNameOrderByReleaseDateDescTitleAsc = bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
//        List<String> collect = allByAuthor_firstNameAndAuthor_lastNameOrderByReleaseDateDescTitleAsc.stream().map(book ->
//                String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies())).
//                collect(Collectors.toList());

//        return collect;

        //with QUERY:
        List<Book> collectedBooks = this.bookRepository
                .findAllByAuthorsNamesThenOrderByReleaseDateDescThenByTitleAsc(firstName, lastName);
        List<String> collectedBooksInfo = collectedBooks
                .stream()
                .map(book ->
                {
                    return String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies());
                }).collect(Collectors.toList());

        return collectedBooksInfo;
    }


    private Book createBookFromInputBookInfo(String[] eachBookInfo) {

        String[] currentBookInfo = eachBookInfo;

        //EditionType.values() => връща масив => [n] => ....
        EditionType editionType = EditionType
                .values()[Integer.parseInt(currentBookInfo[0])];

        LocalDate releaseDate = LocalDate
                .parse(currentBookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(currentBookInfo[2]);

        BigDecimal price = new BigDecimal(currentBookInfo[3]);

        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(currentBookInfo[4])];

        String title = Arrays
                .stream(currentBookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));
        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService.getRandomCategories();
        Book book = new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
        return book;
    }
}
