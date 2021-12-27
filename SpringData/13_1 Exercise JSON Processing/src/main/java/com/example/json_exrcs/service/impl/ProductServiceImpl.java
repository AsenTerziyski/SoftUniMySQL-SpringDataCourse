package com.example.json_exrcs.service.impl;

import com.example.json_exrcs.constants.ProjectConstants;
import com.example.json_exrcs.model.dto.ProductNameAndPriceDto;
import com.example.json_exrcs.model.dto.ProductSeedDto;
import com.example.json_exrcs.model.dto.PR100.ProductsWithBuyerAndSeller;
import com.example.json_exrcs.model.entity.Product;
import com.example.json_exrcs.model.entity.User;
import com.example.json_exrcs.repository.ProductRepository;
import com.example.json_exrcs.service.CategoryService;
import com.example.json_exrcs.service.ProductService;
import com.example.json_exrcs.service.UserService;
import com.example.json_exrcs.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    //    private static final String productFile = "products.json";
    private final ProductRepository productRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() > 0) {
            System.out.println("*********************************Product data already seeded*********************************");
            return;
        }
        String resourcesFilePath = ProjectConstants.PRODUCTS_FILE_FULL_PATH;
        String fileContent = Files.readString(Path.of(resourcesFilePath));
        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(this.validationUtil::isValid)
                .map(productSeedDto ->
                {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    User randomSeller = this.userService.findRandomUser();
                    product.setSeller(randomSeller);

                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        User randomBuyer = this.userService.findRandomUser();
                        if (!randomBuyer.getLastName().equals(randomSeller.getLastName())) {
                            product.setBuyer(randomBuyer);
                        }
                    }
                    product.setCategories(this.categoryService.findRandomCategories());
                    return product;
                })
                .forEach(this.productRepository::save);
        System.out.println("Product data seeded");
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeAndOrderByPrice(BigDecimal low, BigDecimal upper) {
        List<Product> allByPriceBetweenOrderByPriceDesc = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(low, upper);

        List<ProductNameAndPriceDto> collect = allByPriceBetweenOrderByPriceDesc.stream().map(product ->
        {

            ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(product, ProductNameAndPriceDto.class);

            productNameAndPriceDto
                    .setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

            return productNameAndPriceDto;

        })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<ProductsWithBuyerAndSeller> findAllProductsWithBuyerAndSellerAreNotNull() {
        List<Product> products = this.productRepository.findAllByBuyerIsNotNullAndSellerIsNotNullOrderByPriceDesc();
        List<ProductsWithBuyerAndSeller> productsWithBuyerAndSeller = products.stream().map(product -> modelMapper
                .map(product, ProductsWithBuyerAndSeller.class))
                .collect(Collectors.toList());
        return productsWithBuyerAndSeller;
    }


}
