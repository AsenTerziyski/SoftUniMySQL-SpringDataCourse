package com.example.xml_xrcs.service.impl;

import com.example.xml_xrcs.model.dto.CategorySeedDto;
import com.example.xml_xrcs.model.entity.Category;
import com.example.xml_xrcs.repository.CategoryRepository;
import com.example.xml_xrcs.service.CategoryService;
import com.example.xml_xrcs.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories.stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(this.categoryRepository::save);
        System.out.println("************************************************************************************************************************************************************************");
        System.out.println("Successfully seeded CATEGORIES.");
        System.out.println("************************************************************************************************************************************************************************");
    }

    @Override
    public long getCategoryEntityCount() {
        return this.categoryRepository.count();
    }

    @Override
    public Set<Category> getSetOfRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int count = 2;
        long categoryEntityCount = this.getCategoryEntityCount();
        for (int i = 0; i < count; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1L, categoryEntityCount + 1L);
            Category randomCategory = this.categoryRepository
                    .findById(randomId)
                    .orElse(null);
            categories.add(randomCategory);
        }
        return categories;
    }
}
