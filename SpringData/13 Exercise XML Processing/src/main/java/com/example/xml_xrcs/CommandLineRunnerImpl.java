package com.example.xml_xrcs;

import com.example.xml_xrcs.model.dto.CategorySeedRootDto;
import com.example.xml_xrcs.model.dto.ProductRootSeedDto;
import com.example.xml_xrcs.model.dto.UserSeedRootDto;
import com.example.xml_xrcs.service.CategoryService;
import com.example.xml_xrcs.service.ProductService;
import com.example.xml_xrcs.service.UserService;
import com.example.xml_xrcs.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    // XXXXXXXXXXXXXXXXXXXXXXXXXXxxxxxxxxxxxx FILES PATHS XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    private static final String RESOURCE_FILE_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    private final XmlParser xmlParser;

    // *********************************************services********************************************
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    // *************************************************************************************************

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws JAXBException, FileNotFoundException {

        //category seed
        if (this.categoryService.getCategoryEntityCount() == 0) {
            String categoriesFullFilePath = RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME;
            seedCategoriesInDataBase(categoriesFullFilePath);
        } else {
            System.out.println("*********************************************************************************************************************************************************************");
            System.out.println("Categories already seeded!");
            System.out.println("*********************************************************************************************************************************************************************");
        }

        //user seed
        if (this.userService.getUserEntityCount() == 0) {
            String usersFullPath = RESOURCE_FILE_PATH + USERS_FILE_NAME;
            seedUsersInDataBase(usersFullPath);
        } else {
            System.out.println("*********************************************************************************************************************************************************************");
            System.out.println("Users already seeded!");
            System.out.println("*********************************************************************************************************************************************************************");
        }

        //products seed
        if (this.productService.getProductEntitiesCount() == 0) {
            seedProductsInDataBase();
        } else {
            System.out.println("*********************************************************************************************************************************************************************");
            System.out.println("Products already seeded!");
            System.out.println("*********************************************************************************************************************************************************************");
        }

    }

    private void seedProductsInDataBase() throws JAXBException, FileNotFoundException {
        String productsFullPath = RESOURCE_FILE_PATH + PRODUCTS_FILE_NAME;
        ProductRootSeedDto productRootSeedDto = xmlParser.readFromFile(productsFullPath, ProductRootSeedDto.class);
        this.productService.seedProducts(productRootSeedDto.getProducts());
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    private void seedUsersInDataBase(String usersFullPath) throws JAXBException, FileNotFoundException {
        UserSeedRootDto userSeedRootDto = xmlParser.readFromFile(usersFullPath, UserSeedRootDto.class);
        this.userService.seedUsers(userSeedRootDto.getUsers());
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    private void seedCategoriesInDataBase(String categoriesFullFilePath) throws JAXBException, FileNotFoundException {
        CategorySeedRootDto categorySeedRootDto = xmlParser.readFromFile(categoriesFullFilePath, CategorySeedRootDto.class);
        this.categoryService.seedCategories(categorySeedRootDto.getCategories());
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
