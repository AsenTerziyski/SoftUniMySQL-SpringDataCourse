package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class CommandLineImpl implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter problem number:");
        int problemNumber = Integer.parseInt(scanner.nextLine());
        switch (problemNumber) {

            case 1:
                System.out.print("Problem 01: ");
                System.out.println("Enter shampoo size:");
                String inputSize = scanner.nextLine();
                Size size = Size.valueOf(inputSize.toUpperCase(Locale.ROOT));
                List<Shampoo> allBySizeOrderById = this.shampooService.findAllBySizeOrderById(size);
                allBySizeOrderById.stream().forEach(System.out::println);
                break;
            case 2:
                System.out.print("Problem 02: ");
                System.out.println("Enter shampoo size and shampoo label:");
                String inputSize02 = scanner.nextLine();
                Size size02 = Size.valueOf(inputSize02.toUpperCase(Locale.ROOT));
                Label label = new Label();
                Long inputLabelId = Long.parseLong(scanner.nextLine());
                label.setId(inputLabelId);
                List<Shampoo> allBySizeOrLabelOrderByPriceAsc = this.shampooService.findAllBySizeOrLabelOrderByPriceAsc(size02, label);
                allBySizeOrLabelOrderByPriceAsc.stream().forEach(System.out::println);
                break;
            case 3:
                System.out.print("Problem 03: ");
                System.out.println("Enter shampoo min shampoo price: ");
                BigDecimal minPrice = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
                List<Shampoo> allByPriceGreaterThanOrderByPriceDesc = this.shampooService.findAllByPriceGreaterThanOrderByPriceDesc(minPrice);
                allByPriceGreaterThanOrderByPriceDesc.forEach(System.out::println);
                break;
            case 4:
                System.out.print("Problem 04: ");
                System.out.println("Select Ingredients by Name");
                System.out.println("Enter letters that shampoo name starts with");
                String input = scanner.nextLine();
                List<Ingredient> allByNameStartingWith = ingredientService.findAllByNameStartingWith(input);
                allByNameStartingWith.forEach(System.out::println);
                break;
            case 5:
                System.out.print("Problem 05: ");
                System.out.println("Select Ingredients by Names");
                List<String> inputNames = new ArrayList<>();
                String inputName = scanner.nextLine();

                while (true) {
                    inputNames.add(inputName);
                    inputName = scanner.nextLine();
                    if (inputName == null || inputName.trim().isEmpty()) {
                        break;
                    }
                }

                List<Ingredient> allByNameIn = this.ingredientService.findAllByNameIn(inputNames);
                for (Ingredient ingredient : allByNameIn) {
                    System.out.println(ingredient);
                }
                break;
            case 6:
                System.out.println("Problem 06:  Count Shampoos by Price");
                System.out.println("Enter shampoo price");
                String inputPrice = scanner.nextLine();
                BigDecimal price = new BigDecimal(inputPrice);
                int number = this.shampooService.countAllByPriceLessThan(price);
                System.out.println(number);
                break;
            case 7:
                System.out.println("Problem 07: ");
                List<String> ingredientsNames = Arrays.asList(scanner.nextLine().split("\\s++"));
                this.shampooService.findAllByIngredientsNames(ingredientsNames)
                        .forEach(shampoo -> System.out.println(shampoo.getBrand()));
                break;
            case 8:
                System.out.println("Problem 08: ");
                System.out.print("Enter number = ");
                int  n = Integer.parseInt(scanner.nextLine());
                List<Shampoo> allByIngredientsCounts = this.shampooService.findAllByIngredientsCounts(n);
                allByIngredientsCounts.stream().forEach(shampoo -> System.out.println(shampoo.getBrand()));
                break;
            case 9:
                System.out.println("Problem 09: ");
                System.out.print("Enter name = ");
                String name = scanner.nextLine();
                this.ingredientService.deleteAllByName(name);
                break;
            case 10:
                System.out.println("Problem 10: ");
                this.ingredientService.updatePrice();
                break;
            case 11:
                System.out.println("Problem 10: ");

                List<String> ingNames = Arrays.stream(scanner.nextLine().split("\\s+")).toList();

                this.ingredientService.updateAllIngredientsWhereNamesAreIn(ingNames);


                break;
            default:
                throw new IllegalStateException("Unexpected value: " + problemNumber);
        }

    }
}
