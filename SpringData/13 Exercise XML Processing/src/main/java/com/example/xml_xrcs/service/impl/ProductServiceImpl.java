package com.example.xml_xrcs.service.impl;

import com.example.xml_xrcs.model.dto.ProductSeedDto;
import com.example.xml_xrcs.model.entity.Category;
import com.example.xml_xrcs.model.entity.Product;
import com.example.xml_xrcs.model.entity.User;
import com.example.xml_xrcs.repository.ProductRepository;
import com.example.xml_xrcs.service.CategoryService;
import com.example.xml_xrcs.service.ProductService;
import com.example.xml_xrcs.service.UserService;
import com.example.xml_xrcs.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public long getProductEntitiesCount() {
        return this.productRepository.count();
    }

    @Override
    public void seedProducts(List<ProductSeedDto> products) {
        products.stream().filter(productSeedDto -> validationUtil.isValid(productSeedDto))
                .map(this::mapProduct)
                .forEach(this.productRepository::save);
        System.out.println("************************************************************************************************************************************************************************");
        System.out.println("* SUCCESSFULLY SEEDED PRODUCTS. *");
        System.out.println("************************************************************************************************************************************************************************");
    }

    private Product mapProduct(ProductSeedDto productSeedDto) {
        Product product = modelMapper.map(productSeedDto, Product.class);
        User randomSeller = this.userService.getRandomUser();
        product.setSeller(randomSeller);
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
            User randomBuyer = this.userService.getRandomUser();
            product.setBuyer(randomBuyer);
        }
        Set<Category> setOfRandomCategories = this.categoryService.getSetOfRandomCategories();
        product.setCategories(setOfRandomCategories);
        return product;
    }
}
