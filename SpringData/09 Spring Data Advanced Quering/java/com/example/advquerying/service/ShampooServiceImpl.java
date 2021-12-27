package com.example.advquerying.service;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelOrderByPriceAsc(Size size, Label label) {
        return this.shampooRepository.findAllBySizeOrLabelOrderByPriceAsc(size, label);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findAllByIngredientsNames(List<String> ingredientsNames) {
        return this.shampooRepository.findAllByIngredientsNames(ingredientsNames);
    }

    @Override
    public List<Shampoo> findAllByIngredientsCounts(int n) {
        return this.shampooRepository.findAllByIngredientsCounts(n);
    }

//    @Override
//    public List<String> findAllByIngredientsNames(List<String> ingredientsNames) {
//        return this.shampooRepository.findAllByIngredientsNames(ingredientsNames)
//                .stream()
//                .map(Shampoo::getBrand)
//                .collect(Collectors.toList());
//    }
}
