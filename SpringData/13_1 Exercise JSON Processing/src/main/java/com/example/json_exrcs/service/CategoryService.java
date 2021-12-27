package com.example.json_exrcs.service;

import com.example.json_exrcs.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();

    List<Category> findCategoriesByProductsCount();

}
