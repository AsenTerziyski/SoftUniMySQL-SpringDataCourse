package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookTitlesByGivenAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllBookTitlesWithCopiesNumberLessThan5000();


    List<String> findAllBooksWithPriceLessThan5OrMoreThan40();

    List<String> findAllBooksThatAreNotReleasedInYear(int year);

    List<String> findAllBooksByReleaseDateBefore(LocalDate inputDate);

    List<String> findBooksThatContainAStringInTheirTitles(String inputString);

    List<String> findAllBooksByAuthorWhoseLastNameStartsWith(String inputString);


    int findAllBooksWithTitleLongerThan(int minLength);

    void changePrice(long bookId);

    int increaseBookCopiesAfterDate(LocalDate localDate, Integer copiesToAdd);

    List<String> findBookInfo(String bookTitle);

    int removeBooksWithCopiesLowerThan(Integer copies);
}

