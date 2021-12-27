package com.example.json_exrcs.service.impl;

import com.example.json_exrcs.constants.ProjectConstants;
import com.example.json_exrcs.model.dto.CategorySeedDto;
import com.example.json_exrcs.model.entity.Category;
import com.example.json_exrcs.repository.CategoryRepository;
import com.example.json_exrcs.service.CategoryService;
import com.example.json_exrcs.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
//    private static final String categoriesFile = "categories.json";
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        //с проверката по-долу -> ако вече има сийдната дата => няма да сийдва!
        if (this.categoryRepository.count() > 0) {
            System.out.println("Category data already seeded");
            return;
        }
        String resourcesFilePath = ProjectConstants.CATEGORIES_FILE_FULL_PATH;
        String fileContent = Files.readString(Path.of(resourcesFilePath));
        CategorySeedDto[] categorySeedDtos = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays
                .stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);

//        Arrays
//                .stream(categorySeedDtos)
//                .filter(categorySeedDto -> this.validationUtil.isValid(categorySeedDto))
//                .map(categorySeedDto -> this.modelMapper.map(categorySeedDto, Category.class))
//                .forEach(category -> this.categoryRepository.save(category));

        System.out.println("Category data seeded");
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> randomCategories = new HashSet<>();
        long categoriesCount = this.categoryRepository.count();
        int catCount = ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom
                    .current()
                    .nextLong(1, categoriesCount+1);
            Category byId = this.categoryRepository
                    .findById(randomId)
                    .orElse(null);

            randomCategories.add(byId);
        }
        return randomCategories;
    }

    @Override
    public List<Category> findCategoriesByProductsCount() {
        List<Category> categories = this.categoryRepository.problem03();
        return categories;
    }


}
