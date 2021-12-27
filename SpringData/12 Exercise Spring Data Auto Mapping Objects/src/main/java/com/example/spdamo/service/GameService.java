package com.example.spdamo.service;

import com.example.spdamo.model.dto.GameAddDto;
import com.example.spdamo.model.entity.Game;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long gameId, BigDecimal bigDecimal, Double size);

    void deleteGame(Long gameIdToDelete);

    void printTitleAndPriceOfAllGamesInDataBase();

    void findDetailsAboutGameWithTitle(String gameTitle);

    Game findGame(String input);

//    void buyGame(String gameTitleToBuy);
//    void printOwnedGames();

}
