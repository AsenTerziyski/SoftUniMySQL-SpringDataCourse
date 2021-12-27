package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

//    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
//    List<Author> findAllByBooksSizeDESC();

    @Query("SELECT a FROM Author a ORDER BY size(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String endString);


    @Query("select a from Author a where a.lastName like :endString% ")
    List<Author> testA(@Param(value = "endString") String endString);



    // може и така:
//    List<Author> findAllByFirstNameEndsWith(String ednStr);

    //delimiter $$
    //create procedure find_total_book_number_of_given_author_by_first_and_last_name (fn varchar(255), ln varchar(255))
    //begin
    //    select a.first_name, a.last_name, count(b.copies)
    //    from authors as a
    //             join books b on a.id = b.author_id
    //    where a.first_name like fn and a.last_name like ln;
    //end $$
    //delimiter ;


//    @Procedure("find_total_book_number_of_given_author_by_first_and_last_name")
//    Integer findTotalBookNumberOfAuthorByFirstAndLastName(@Param(value = "fn") String fn,
//                                                          @Param(value = "ln") String ln);

    @Query("select count(b.copies) from Book b join Author a on a.id = b.author.id where a.lastName =:fn and a.lastName = :ln")
    Integer findTotalBookNumberOfAuthorByFirstAndLastName(@Param(value = "fn") String fn, @Param(value = "ln") String ln);


}
