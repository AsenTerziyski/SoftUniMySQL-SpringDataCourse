package com.example.json_exrcs;

import com.example.json_exrcs.constants.ProjectConstants;
import com.example.json_exrcs.model.dto.PR200.UserWithFriendsDto;
import com.example.json_exrcs.model.dto.ProductNameAndPriceDto;
import com.example.json_exrcs.model.dto.PR100.ProductsWithBuyerAndSeller;
import com.example.json_exrcs.model.dto.UserSoldDto;
import com.example.json_exrcs.model.entity.Category;
import com.example.json_exrcs.service.CategoryService;
import com.example.json_exrcs.service.ProductService;
import com.example.json_exrcs.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/files/output/";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "pr01_products_in_range.json";
    private static final String SUCCESSFULLY_SOLD_PRODUCTS = "pr02_sold_products.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, BufferedReader bufferedReader, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println("Please enter problem number:");
        int problemNumber = Integer.parseInt(this.bufferedReader.readLine());

        switch (problemNumber) {
            case 1:
                productsInRangeWithoutBuyer();
                break;
            case 2:
                soldProducts();
                break;
            case 3:
                categoriesByProductsCount();
                break;
            case 100:
                allProductsWithBuyerAndSeller();
                break;
            case 200:
                allUsersWhoHaveFriends();
                break;
        }

    }

    private void allUsersWhoHaveFriends() throws IOException {
        List<UserWithFriendsDto> allUsersWithFriends = this.userService.findAllUsersWithFriends();
        String content = this.gson.toJson(allUsersWithFriends);
        String output = ProjectConstants.PR200_OUTPUT_FILE_FULL_PATH;
        writeToFile(output,content);
    }

    private void allProductsWithBuyerAndSeller() throws IOException {
        List<ProductsWithBuyerAndSeller> allProductsWithBuyerAndSellerAreNotNull = this.productService.findAllProductsWithBuyerAndSellerAreNotNull();
        String content = this.gson.toJson(allProductsWithBuyerAndSellerAreNotNull);
        String output = ProjectConstants.PR100_OUTPUT_FILE_FULL_PATH;
        writeToFile(output, content);

    }

//    private void findAllUsersWithFriends() {
//        List<UsersWithFriendsDto> usersWithFriends = this.userService.findUsersWithFriends();
//
//
//    }

    private void setUsersFriends() {
        this.userService.setRandomFriendsOfUsers();
    }

    private void categoriesByProductsCount() {
        List<Category> categoriesByProductsCount = this.categoryService
                .findCategoriesByProductsCount();
        System.out.println();
    }


    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = this.userService.findAllUsersWhoSoldMoreThanProduct();
        String content = this.gson.toJson(userSoldDtos);
        String output = ProjectConstants.PR01_PRODUCTS_IN_RANGE_FILE_FULL_PATH;
        writeToFile(output, content);
    }

    private void productsInRangeWithoutBuyer() throws IOException {

        BigDecimal low = BigDecimal.valueOf(500L);
        BigDecimal upper = BigDecimal.valueOf(1000L);

        List<ProductNameAndPriceDto> productDtos = this.productService.findAllProductsInRangeAndOrderByPrice(low, upper);

        String inputContent = this.gson.toJson(productDtos);
        String output = ProjectConstants.PR01_PRODUCTS_IN_RANGE_FILE_FULL_PATH;

        writeToFile(output, inputContent);
    }

    private void writeToFile(String outputPath, String content) throws IOException {
        Files.write(Path.of(outputPath), Collections.singleton(content));
    }


    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.userService.seedUsers();
        this.productService.seedProducts();

    }
}
