package com.example.spdamo.service.impl;

import com.example.spdamo.model.dto.GameAddDto;
import com.example.spdamo.model.entity.Game;
import com.example.spdamo.repository.GameRepository;
import com.example.spdamo.service.GameService;
import com.example.spdamo.service.UserService;
import com.example.spdamo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Modifying
    @Override
    public void addGame(GameAddDto gameAddDto) {

        if (!userService.hasLoggedUser()) {
            return;
        }

        Set<ConstraintViolation<GameAddDto>> violation = this.validationUtil.getViolation(gameAddDto);
        if (!violation.isEmpty()) {
            violation.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            return;
        }

        if (this.userService.getLoggedUser().getAdmin()) {
            Game game = modelMapper.map(gameAddDto, Game.class);
//        String releaseDate = gameAddDto.getReleaseDate();
//        LocalDate parsedDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        game.setReleaseDate(parsedDate);
            this.gameRepository.save(game);
            System.out.println("Added game " + gameAddDto.getTitle());
        } else {
            System.out.println(this.userService.getLoggedUser().getFullName() + "is not ADMIN and has not rights to add game.");
        }
    }

    @Modifying
    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {

        if (this.userService.getLoggedUser().getAdmin()) {
            Game game = this.gameRepository.findById(gameId).orElse(null);
            if (game == null) {
                System.out.println("There is no game with this ID.");
                return;
            }
            game.setPrice(price);
            game.setSize(size);
            gameRepository.save(game);
            System.out.println("Edited " + game.getTitle());
        } else {
            System.out.println(this.userService.getLoggedUser().getFullName() + "is not ADMIN and had no rights to delete game.");
        }

    }

    @Modifying
    @Override
    public void deleteGame(Long gameIdToDelete) {
        if (!userService.hasLoggedUser()) {
            return;
        }
        if (this.userService.getLoggedUser().getAdmin()) {
            Game game = this.gameRepository.findById(gameIdToDelete).orElse(null);
            if (game == null) {
                System.out.println("There is no game with this ID.");
                return;
            }
            this.gameRepository.delete(game);
            System.out.println("Deleted " + game.getTitle());
        } else {
            System.out.println(this.userService.getLoggedUser().getFullName() + "is not ADMIN and had no rights to edit game.");
        }

    }

    @Override
    public void printTitleAndPriceOfAllGamesInDataBase() {
        List<Game> allGamesFullInfo = this.gameRepository.findAll();
        if (allGamesFullInfo.isEmpty()) {
            System.out.println("No games in DataBase!");
            return;
        }
        allGamesFullInfo.forEach(game -> {
            String title = game.getTitle();
            BigDecimal price = game.getPrice();
            System.out.println(String.format("%s %.2f", title, price));
        });
    }

    @Override
    public void findDetailsAboutGameWithTitle(String gameTitle) {
        Game byTitle = this.gameRepository.getByTitle(gameTitle);
        if (byTitle == null) {
            System.out.println("There is no game with this title.");
            return;
        }
        System.out.println(byTitle.getTitle());
        System.out.println(String.format("%.2f", byTitle.getPrice()));
        System.out.println(byTitle.getDescription());
        System.out.println(byTitle.getReleaseDate());
    }

    @Override
    public Game findGame(String input) {
        return this.gameRepository.getByTitle(input);
    }

//    @Override
//    public void buyGame(String gameTitleToBuy) {
//    }

//    @Override
//    public void printOwnedGames() {
//        if (!this.userService.hasLoggedUser()) {
//            return;
//        }
//        List<Game> allOwnedGames = this.gameRepository.findAll();
//
//    }
}
