package com.example.advquerying.service;


import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;

import java.util.Collection;
import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllByNameStartingWith(String letter);
    List<Ingredient> findAllByNameIn(Collection<String> name);
    void deleteAllByName(String name);
    void updatePrice();
    void updateAllIngredientsWhereNamesAreIn(Collection<String > ingredientsNames);


}
