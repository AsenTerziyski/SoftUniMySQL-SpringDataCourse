package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameStartingWith(String letter);

    List<Ingredient> findAllByNameIn(Collection<String> name);


    @Query("delete  from Ingredient i where i.name = :name")
    @Modifying
    void deleteAllByName(String name);

    @Query("update Ingredient i set i.price = i.price * 1.1")
    @Modifying
    void updatePrice();

    @Query ("update Ingredient i set i.price = i.price * 100 where i.name in :ingredientsNames")
    @Modifying
    void updateAllIngredientsWhereNamesAreIn(Collection<String > ingredientsNames);

}
