package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice);

//    @Query("select b from Book b where b.price not between :lowPrice and :highPrice")
//    List<Book> findAllByPriceLowerThan5AndGreaterThan40
//            (@Param(value = "lowPrice") BigDecimal lowPrice, @Param(value = "highPrice") BigDecimal highPrice);


//    @Query("select book from Book book where book.price not between :lowPrice and :highPrice ORDER BY book.price desc")
//    List<Book> findAllByPriceLowerThanHighBoundaryPriceAndGreaterThanLowerBoundaryPrice(@Param(value = "lowPrice") BigDecimal highPrice,
//                                                                                        @Param(value = "highPrice") BigDecimal lowPrice);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate releaseDateBefore, LocalDate releaseDateAfter);

//    List<Book> findAllByTitleIsContaining(String stringInTitle);

    List<Book> findAllByTitleIsContaining(String stringInTitle);

//
//    @Query("select b from Book b join Author as a on b.author.id = a.id group by b.id having b.author.firstName like :str%")
//    List<Book> testFindByA(@Param(value = "str") String startString);

    List<Book> findAllByAuthor_LastNameStartsWith(String startString);

    @Query("select count(b) from Book b where length(b.title) > :length")
    int countOfBooksWithTitleLongerThan(@Param(value = "length") int titleLength);

    //test
//    @Query("select count(b) from Book b where length(b.title) > : l ")
//    int testCount(@Param(value = "l") int tl);

//    @Procedure("change_book_price_by_id")
//    void changeBookPriceById(Long book_id);

    @Procedure("rise_book_price_of_book_with_given_id")
    void changeBookPriceByIdWith1000(Long book_id);

    @Modifying
    @Query("update Book b set b.copies = b.copies + :copiesToAdd where b.releaseDate> :afterDate")
    int updateCopiesByReleaseDate(@Param(value = "copiesToAdd") Integer copiesToAdd, @Param(value = "afterDate") LocalDate afterDate);

    List<Book> findAllByTitle(String searchedTitle);

    @Modifying
    List<Book> removeBookByCopiesLessThan(Integer copies);


}
