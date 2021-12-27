package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Author;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService,
                                 AuthorService authorService,
                                 BookService bookService,
                                 BufferedReader bufferedReader) throws IOException {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        // from old exercise:
        seedData();
        // this exercise:
        System.out.print("Please enter problem No:");
        int problemNumber = Integer.parseInt(bufferedReader.readLine());
        switchProblem(problemNumber);
        // old exercise:
//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
    }

    private void pr15changeBookPriceByGivenId() throws IOException {
        System.out.println("Enter the book id that you want to change the price");
        long bookId = Long.parseLong(bufferedReader.readLine());
        this.bookService.changePrice(bookId);
    }

    private void pr14() throws IOException {
        System.out.println("Enter authors first and last name:");
        String[] names = bufferedReader.readLine().split("\\s+");
        System.out.println("Enter authors last name:");
        String firstName = names[0];
        String lastName = names[1];
        System.out.println();
        Integer authorsTotalBookNumber = this.authorService.findAuthorsTotalBookNumber(firstName, lastName);
        System.out.println(authorsTotalBookNumber);
    }

    private void pr13removeBooksWithNumberOfCopiesLessThan() throws IOException {
        System.out.println("Enter the number of copies:");
        int copies = Integer.parseInt(bufferedReader.readLine());
        int affectedNumber = this.bookService.removeBooksWithCopiesLowerThan(copies);
        System.out.println("Total books removed => " + affectedNumber + "!");
    }


    private void pr12increaseCopiesOfAllBooksReleaseAfterAGivenDateAndAGivenNumberAndPrintTotalAmountOfAddedBookCopiesThat() throws IOException {
        System.out.println("Please enter date int format dd MMM yyyy (for example 10 Sep 2015)");
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .withLocale(Locale.ENGLISH);
        String inputDateInStringFormat = bufferedReader.readLine();
        LocalDate localDate = LocalDate.parse(inputDateInStringFormat, formatter);
        System.out.println("Please enter copies to add:");
        Integer copiesToAdd = Integer.parseInt(bufferedReader.readLine());
        System.out.println(this.bookService.increaseBookCopiesAfterDate(localDate, copiesToAdd));
    }

    private void pr11printBookInfoByBookTitle() throws IOException {
        System.out.println("Please enter the book title you are looking for:");
        String bookTitle = bufferedReader.readLine();
        this.bookService.findBookInfo(bookTitle).forEach(System.out::println);
    }

    private void pr10printTotalBookTotalNumberOfCopiesByAuthorAndOrderThemDescendingByTotalBookCopies() {
        this.authorService.findAllAuthorsAndTheirTotalSoldBookCopies().forEach(System.out::println);
    }

    private void pr09printTheNumberOfBooksWithTitleLongerThanAGivenLength() throws IOException {
        System.out.println("Please enter required min title length: ");
        int minLength = Integer.parseInt(bufferedReader.readLine());
        if (minLength <= 0) {
            throw new IllegalArgumentException("Input length can not be zero or negative");
        }
        int count = this.bookService.findAllBooksWithTitleLongerThan(minLength);
        if (count == 0) {
            System.out.println(String.format("There are no books with titles longer than %d!", minLength));
            return;
        }
        System.out.print(count);
        System.out.println(String.format(" => There are %d longer than %d symbols.", count, minLength));
    }

    private void pr08printTitlesOfBooksWrittenByAuthorsWhoseLastNameStartsWithAGivenString() throws IOException {
        System.out.println("Please enter the string that last name of author may start with:");
        String inputString = bufferedReader.readLine();
        this.bookService.findAllBooksByAuthorWhoseLastNameStartsWith(inputString).forEach(System.out::println);
    }

    private void pr07printsTheTitlesOfBooksContainingAGivenString() throws IOException {
        System.out.println("Please enter the string that title of books may contain: ");
        String inputString = bufferedReader.readLine();
        if (inputString == null || inputString.trim().isEmpty()) {
            throw new IllegalArgumentException("String can not be empty or null!");
        }
        this.bookService
                .findBooksThatContainAStringInTheirTitles(inputString)
                .stream()
                .forEach(System.out::println);
    }

    private void pr06printAuthorNamesWhoseFirstNameEndsWithAGivenString() throws IOException {
        System.out.println("Please enter the string that author's first name ends with: ");
        String inputString = bufferedReader.readLine();
        this.authorService
                .findAllAuthorsNamesWhoseFirstNameEndsWith(inputString)
                .forEach(System.out::println);
    }

    private void pr05printBooksTitlesAndEditionTypesAndPriceByReleaseDateBeforeAGivenDate() throws IOException {
        System.out.println("Please enter date in format dd-MM-yyyy : ");

        String input = bufferedReader.readLine();
        LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService
                .findAllBooksByReleaseDateBefore(inputDate)
                .forEach(System.out::println);
    }

    private void pr04printAllBookTitlesThatAreNotReleased() throws IOException {
        System.out.println("Please enter year:");
        int year = Integer.parseInt(bufferedReader.readLine());
        this.bookService.findAllBooksThatAreNotReleasedInYear(year).forEach(System.out::println);
    }

    private void pr03printTitlesAndPricesOfBooksWithPriceLowerThan5AndHigherThan40() {
        this.bookService.findAllBooksWithPriceLessThan5OrMoreThan40().forEach(System.out::println);
    }

    private void pr02printTheTitlesOfTheGoldenEditionBooks() {
        this.bookService.findAllBookTitlesWithCopiesNumberLessThan5000().forEach(System.out::println);
    }

    private void pr01printBooksTitlesByAgeRestriction() throws IOException {
        System.out.println("Please enter age restriction. Choose between MINOR, TEEN or ADULT:");
        String inputAgeRestriction = (bufferedReader.readLine()).toUpperCase();
        if (!inputAgeRestriction.equals("MINOR") && !inputAgeRestriction.equals("TEEN")
                && !inputAgeRestriction.equals("ADULT")) {
            throw new IllegalArgumentException("Your input age restriction is not valid!");
        }
        AgeRestriction ageRestriction = AgeRestriction
                .valueOf(inputAgeRestriction);
        this.bookService
                .findAllBookTitlesByGivenAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void switchProblem(int problemNumber) throws IOException {
        switch (problemNumber) {
            case 1:
                pr01printBooksTitlesByAgeRestriction();
                break;
            case 2:
                pr02printTheTitlesOfTheGoldenEditionBooks();
                break;
            case 3:
                pr03printTitlesAndPricesOfBooksWithPriceLowerThan5AndHigherThan40();
                break;
            case 4:
                pr04printAllBookTitlesThatAreNotReleased();
                break;
            case 5:
                pr05printBooksTitlesAndEditionTypesAndPriceByReleaseDateBeforeAGivenDate();
                break;
            case 6:
                pr06printAuthorNamesWhoseFirstNameEndsWithAGivenString();
                break;
            case 7:
                pr07printsTheTitlesOfBooksContainingAGivenString();
                break;
            case 8:
                pr08printTitlesOfBooksWrittenByAuthorsWhoseLastNameStartsWithAGivenString();
                break;
            case 9:
                pr09printTheNumberOfBooksWithTitleLongerThanAGivenLength();
                break;
            case 10:
                pr10printTotalBookTotalNumberOfCopiesByAuthorAndOrderThemDescendingByTotalBookCopies();
                break;
            case 11:
                pr11printBookInfoByBookTitle();
                break;
            case 12:
                pr12increaseCopiesOfAllBooksReleaseAfterAGivenDateAndAGivenNumberAndPrintTotalAmountOfAddedBookCopiesThat();
                break;
            case 13:
                pr13removeBooksWithNumberOfCopiesLessThan();
                break;
            case 14:
                pr14();
                break;
            case 15:
                pr15changeBookPriceByGivenId();
                break;
            default:
                System.out.println("OOPSSS....");
                throw new IllegalArgumentException("Enter correct number between 1 and 15!");
                /* System.out.println("Enter correct number!"); */
        }
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

//    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
//        bookService
//                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsAndNumberOfTheirBooks() {
//        authorService
//                .getAllAuthorsOrderByCountOfTheirBooks()
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }
//
//    private void printAllBooksAfterYear(int year) {
//        bookService
//                .findAllBooksAfterYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);
//    }
}
