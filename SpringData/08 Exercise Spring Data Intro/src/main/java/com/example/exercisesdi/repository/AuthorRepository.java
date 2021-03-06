package com.example.exercisesdi.repository;

import com.example.exercisesdi.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a order by a.books.size desc")
    List<Author> findAllByBooksCountSizeDesc();

}
