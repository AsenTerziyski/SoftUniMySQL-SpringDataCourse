package com.example.exercisesdi;

import com.example.exercisesdi.service.AuthorService;
import com.example.exercisesdi.service.BookService;
import com.example.exercisesdi.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner  = new Scanner(System.in);
        seedData();

        System.out.println("Please SELECT QUERY NUMBER: ");
        int queryNumber = Integer.parseInt(scanner.nextLine());
        switch (queryNumber) {
            case 1:
                printAllBooksAfterYear(2000);
                break;
            case 2:
                printAllAuthorsNamesOfBooksWithReleaseDateBeforeYear(1990);
                break;
            case 3:
                printAllAuthorsAndCountOfTheirBooks();
                break;
            case 4:
                System.out.println("PLEASE AUTHORS FULL NAME:");
                String[] fullName = scanner.nextLine().split("\\s+");
                printAllBooksByAuthorNameOrderedByReleaseDate(fullName[0], fullName[1]);
                break;
        }
    }

    private void printAllBooksByAuthorNameOrderedByReleaseDate(String firstName, String lastName) {
        List<String> allBooksByAuthorsFirstAndLastNamesOrderedByReleaseDate = bookService.findAllBooksByAuthorsFirstAndLastNamesOrderedByReleaseDate(firstName, lastName);

        System.out.println("***********************************************");
        allBooksByAuthorsFirstAndLastNamesOrderedByReleaseDate
                .stream()
                .forEach(System.out::println);
        System.out.println("***********************************************");


    }


    private void printAllAuthorsAndCountOfTheirBooks() {
        System.out.println("***********************************************");
        authorService
                .getAllAuthorsByCountOfTheirBooks()
                .forEach(System.out::println);
        System.out.println("***********************************************");
    }

    private void printAllAuthorsNamesOfBooksWithReleaseDateBeforeYear(int year) {
        System.out.println("***********************************************");
        List<String> allAuthorsNamesOfBooksWithReleaseDateBeforeYear = bookService.findAllAuthorsNamesOfBooksWithReleaseDateBeforeYear(year);
        allAuthorsNamesOfBooksWithReleaseDateBeforeYear.forEach(System.out::println);
        System.out.println("***********************************************");
    }

    private void printAllBooksAfterYear(int year) {
        System.out.println("***********************************************");
        bookService
                .findAllBooksAfterYear(year)
                .forEach(book -> System.out.println(book.getTitle()));
        System.out.println("***********************************************");
//        bookService
//                .findAllBooksAfterYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
