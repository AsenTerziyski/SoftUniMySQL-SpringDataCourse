package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.AuthorRepository;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row ->
                {
                    String[] bookInfo = row.split("\\s+");
                    Book book = createBookFromInfo(bookInfo);
                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitlesByGivenAgeRestriction(AgeRestriction ageRestriction) {
        List<String> bookTitles = bookRepository
                .findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(book -> book.getTitle())
                .collect(Collectors.toList());

        return bookTitles;
    }

    @Override
    public List<String> findAllBookTitlesWithCopiesNumberLessThan5000() {
        List<String> goldBooks = bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream().map(book -> book.getTitle()).collect(Collectors.toList());
        return goldBooks;
    }

    @Override
    public List<String> findAllBooksWithPriceLessThan5OrMoreThan40() {
        // test
//        List<Book> allByPriceLowerThanHighBoundaryPriceAndGreaterThanLowerBoundaryPrice = bookRepository
//                .findAllByPriceLowerThanHighBoundaryPriceAndGreaterThanLowerBoundaryPrice(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L));
//        return allByPriceLowerThanHighBoundaryPriceAndGreaterThanLowerBoundaryPrice
//                .stream()
//                .map(book ->
//                {
//                    String title = book.getTitle();
//                    BigDecimal price = book.getPrice();
//                    return String.format("%s => with price: $%.2f", title, price);
//                })
//                .collect(Collectors.toList());

        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L))
                .stream()
                .map(book -> String.format("%s -> price: $%.2f",
                        book.getTitle(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksThatAreNotReleasedInYear(int year) {

        LocalDate dateBefore = LocalDate.of(year, 1, 1);
        LocalDate dateAfter = LocalDate.of(year, 12, 31);

        return this.bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(dateBefore, dateAfter)
                .stream()
                .map(book -> {
                    String title = book.getTitle();
                    LocalDate releaseDate = book.getReleaseDate();
                    return String.format("%s - released in %s", title, releaseDate);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByReleaseDateBefore(LocalDate inputDate) {
//        //test
//        return this.bookRepository.findAllByReleaseDateBefore(inputDate)
//                .stream()
//                .sorted((f, s) -> f.getEditionType().name().compareToIgnoreCase(s.getEditionType().name()))
//                .map(book -> book.getTitle() + " => is with edition type: " + book.getEditionType().name())
//                .collect(Collectors.toList());

        return bookRepository
                .findAllByReleaseDateBefore(inputDate)
                .stream()
                .map
                        (book ->
                                {
                                    String title = book.getTitle();
                                    String editionType = book.getEditionType().name();
                                    BigDecimal price = book.getPrice();
                                    return String.format("%s %s %.2f", title, editionType, price);
                                }
                        )
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBooksThatContainAStringInTheirTitles(String inputString) {
        return this.bookRepository
                .findAllByTitleIsContaining(inputString)
                .stream()
                .map(book -> book.getTitle())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorWhoseLastNameStartsWith(String inputString) {
        return this.bookRepository.findAllByAuthor_LastNameStartsWith(inputString)
                .stream()
                .map(b ->
                {
                    String title = b.getTitle();
                    String authorsFirstName = b.getAuthor().getFirstName();
                    String authorsLastName = b.getAuthor().getLastName();
                    String output = String.format("%s ( %s %s )", title, authorsFirstName, authorsLastName);
                    return output;
                })
                .collect(Collectors.toList());

        // variant 2
//        List<Author> authors = this.authorRepository.testA(inputString);
//        List<String> bookTitles = new ArrayList<>();
//
//        authors.forEach(author -> {
//
//            StringBuilder sb = new StringBuilder();
//            String firstName = author.getFirstName();
//            String lastName = author.getLastName();
//
//            author.getBooks().forEach(book -> {
//                String title = book.getTitle();
//                sb.append(title + " => ("+ firstName + " " + lastName +  ")").append(System.lineSeparator());
//                bookTitles.add(sb.toString().trim());
//            });
//        });
//        return bookTitles;
    }


    @Override
    public int findAllBooksWithTitleLongerThan(int minLength) {
        return this.bookRepository.countOfBooksWithTitleLongerThan(minLength);
    }

    @Override
    public void changePrice(long bookId) {
        this.bookRepository.changeBookPriceByIdWith1000(bookId);
//        this.bookRepository.changeBookPriceById(bookId);
    }

    @Override
    // трябва да има анотация @Transactional:
    @Transactional
    public int increaseBookCopiesAfterDate(LocalDate localDate, Integer copiesToAdd) {
        int affectedRows = this.bookRepository.updateCopiesByReleaseDate(copiesToAdd, localDate);
        return affectedRows * copiesToAdd;
    }

    @Override
    public List<String> findBookInfo(String bookTitle) {
        return this.bookRepository
                .findAllByTitle(bookTitle)
                .stream()
                .map(book ->
                {
                    String titleName = book.getTitle();
                    String editionTypeName = book.getEditionType().name();
                    String ageRestrictionName = book.getAgeRestriction().name();
                    BigDecimal bookPrice = book.getPrice();
                    //Things Fall Apart GOLD ADULT 40.02
                    String output = String.format("%s %s %s %.2f", titleName, editionTypeName, ageRestrictionName, bookPrice);
                    return output;
                })
                .collect(Collectors.toList());
    }

    @Override
    // трябва да има анотация @Transactional:
    @Transactional
    public int removeBooksWithCopiesLowerThan(Integer copies) {
        List<Book> books = this.bookRepository.removeBookByCopiesLessThan(copies);
        return books.size();
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
