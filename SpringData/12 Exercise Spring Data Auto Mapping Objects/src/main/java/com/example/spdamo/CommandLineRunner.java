package com.example.spdamo;

import com.example.spdamo.model.dto.GameAddDto;
import com.example.spdamo.model.dto.UserLoginDto;
import com.example.spdamo.model.dto.UserRegisterDto;
import com.example.spdamo.model.entity.Game;
import com.example.spdamo.model.entity.User;
import com.example.spdamo.service.GameService;
import com.example.spdamo.service.UserService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        User currentlyLoggedUser = new User();
        Map<String, Game> currentUserBasket = new HashMap<>();
        int count = 0;
        boolean isAdmin = false;
        currentlyLoggedUser.setAdmin(isAdmin);


        while (true) {

            System.out.println("Enter valid commands / or hit EXIT:");
            String[] input = bufferedReader.readLine().split("\\|");

            if (input[0].equalsIgnoreCase("EXIT")) {
                break;
            }

            String command = input[0].toUpperCase();

            String email = null;
            String password = null;
            String price = null;
            Double size = null;

            switch (command) {
                case "REGISTERUSER":
                    email = input[1];
                    password = input[2];
                    String confirmPassword = input[3];
                    String fullName = input[4];

                    if (count == 0) {
                        isAdmin = true;
                        this.userService.registerUser(new UserRegisterDto(email, password, confirmPassword, fullName, isAdmin));
                    } else {
                        this.userService
                                .registerUser(new UserRegisterDto(email, password, confirmPassword, fullName, isAdmin));
                    }

                    isAdmin = false;
                    count++;
                    break;

                case "LOGINUSER":
                    email = input[1];
                    password = input[2];
                    this.userService.loginUser(new UserLoginDto(email, password));
                    break;

                case "LOGOUT":
                    userService.logout();
                    break;

                case "ADDGAME":
                    String title = input[1];
                    price = input[2];
                    size = Double.parseDouble(input[3]);
                    String trailer = input[4];
                    String thumbnailURL = input[5];
                    String description = input[6];
                    String releaseDate = input[7];

                    this.gameService.addGame(new GameAddDto(title,
                                    new BigDecimal(price),
                                    size, trailer, thumbnailURL, description,
                                    releaseDate));
                    break;

                case "EDITGAME":
                    Long gameId = Long.parseLong(input[1]);
                    String inputPriceInfo = input[2].split("=")[1];
                    price = inputPriceInfo;
                    String inputSizeInfo = input[3].split("=")[1];
                    size = Double.parseDouble(inputSizeInfo);
                    this.gameService.editGame(gameId, new BigDecimal(price), size);
                    break;

                case "DELETEGAME":
                    Long gameIdToDeleted = Long.parseLong(input[1]);
                    this.gameService.deleteGame(gameIdToDeleted);
                    break;

                case "ALLGAMES":
                    this.gameService.printTitleAndPriceOfAllGamesInDataBase();
                    break;

                case "DETAILGAME":
                    String gameTitle = input[1];
                    this.gameService.findDetailsAboutGameWithTitle(gameTitle);
                    break;

                case "OWNEDGAMES":

                    Set<Game> games = currentlyLoggedUser.getGames();
                    if (games == null) {
                        System.out.println("User" + currentlyLoggedUser.getFullName() + "does not own any games");
                    } else {
                        System.out.println("User owns:");
                        for (Game game : games) {
                            System.out.println("    " + game.getTitle());
                        }

                    }
                    break;

                case "ADDITEM":
                    title = input[1];
                    Game addGame = this.gameService.findGame(title);
                    if (addGame != null) {
                        currentUserBasket.putIfAbsent(title, addGame);
                    } else {
                        System.out.println("Adding item unsuccessfully. The item does not exist in data base!");
                    }
                    break;

                case "REMOVEITEM":
                    title = input[1];

                    Game removeGame = this.gameService.findGame(title);
                    long countGamesWithThisTitle = currentUserBasket.entrySet().stream().filter(g -> g.getKey().equals(title)).count();

                    if (countGamesWithThisTitle == 1) {
                        currentUserBasket.remove(title);
                        System.out.println("Successfully removed game with title: " + removeGame.getTitle() + "!");
                    }

                    break;

                case "BUYITEM":
                    Set<Game> buyGgames = new HashSet<>();

                    if (currentUserBasket != null) {
                        currentUserBasket.forEach((key, value) -> buyGgames.add(value));
                    } else {
                        System.out.println("User's basket is empty. Nothing bought");
                    }
                    currentlyLoggedUser.setGames(buyGgames);
                    break;

                default:
                    System.out.println("Enter valid command or hit EXIT");
                    break;
            }
        }
    }

}
