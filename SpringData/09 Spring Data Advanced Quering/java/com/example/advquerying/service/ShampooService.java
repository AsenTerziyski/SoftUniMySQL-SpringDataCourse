package com.example.advquerying.service;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findAllBySizeOrderById(Size size);
    List<Shampoo> findAllBySizeOrLabelOrderByPriceAsc(Size size, Label label);
    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    int countAllByPriceLessThan(BigDecimal price);

//    @Query("SELECT s  FROM Shampoo s JOIN s.ingredients i WHERE i.name in :names")
    List<Shampoo> findAllByIngredientsNames(List<String> ingredientsNames);

//    @Query("select s from Shampoo s join s.ingredients i where count(i) < :n")
    List<Shampoo> findAllByIngredientsCounts(int n);
}
