package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartingWith(String letter) {
        return this.ingredientRepository.findAllByNameStartingWith(letter);
    }

    @Override
    public List<Ingredient> findAllByNameIn(Collection<String> name) {
        return this.ingredientRepository.findAllByNameIn(name);
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updatePrice() {
        this.ingredientRepository.updatePrice();

    }

    @Override
    @Transactional
    public void updateAllIngredientsWhereNamesAreIn(Collection<String> ingredientsNames) {
        this.ingredientRepository.updateAllIngredientsWhereNamesAreIn(ingredientsNames);
    }


}
