package com.example.xml_xrcs.service;

import com.example.xml_xrcs.model.dto.CategorySeedDto;
import com.example.xml_xrcs.model.dto.UserSeedDto;
import com.example.xml_xrcs.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);
    long getCategoryEntityCount();
    Set<Category> getSetOfRandomCategories();


}
