package com.example.exercisesdi.repository;

import com.example.exercisesdi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);
    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);
    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(
            String author_firstName, String author_lastName);
    @Query ("select b from Book b where b.author.firstName = ?1 and b.author.lastName = ?2")
    List<Book> findAllByAuthorsNamesThenOrderByReleaseDateDescThenByTitleAsc(String aFirstName, String aLastName);


}