package com.example.advquerying.repositories;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelOrderByPriceAsc(Size size, Label label);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s  FROM Shampoo s JOIN s.ingredients i WHERE i.name in :names")
    List<Shampoo> findAllByIngredientsNames(List<String> names);


//    @Query("select s from Shampoo s join s.ingredients i group by s.id having count(i) < :n")
//    List<Shampoo> findAllByIngredientsCounts(long n);

    @Query("select s from Shampoo s where s.ingredients.size < :n")
    List<Shampoo> findAllByIngredientsCounts(int n);


}

