package com.example.football.service.impl;

import com.example.football.models.dto.xmldtos.PlayerSeedXmlDto;
import com.example.football.models.dto.xmldtos.PlayersSeedXmlRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.models.entity.enums.Position;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.xmlparserfolder.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {


    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    private final TownRepository townRepository;
    private final StatRepository statRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownRepository townRepository, StatRepository statRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlayersSeedXmlRootDto playersSeedXmlRootDto = this.xmlParser.fromFile(PLAYERS_FILE_PATH, PlayersSeedXmlRootDto.class);
        List<PlayerSeedXmlDto> validP = playersSeedXmlRootDto.getPlayers().stream().filter(playerSeedXmlDto -> {
            boolean validPlayer = this.validationUtil.isValid(playerSeedXmlDto);
            if (validPlayer) {
                Position position = playerSeedXmlDto.getPosition();
                String format = String.format("Successfully imported Player %s %s - %s", playerSeedXmlDto.getFirstName(), playerSeedXmlDto.getLastName(), playerSeedXmlDto.getPosition());
                sb.append(format).append(System.lineSeparator());
            } else {
                sb.append("Invalid Player");
            }
            return validPlayer;
        }).collect(Collectors.toList());
        System.out.println();
        List<Player> collect = validP.stream().map(playerSeedXmlDto -> {
            Player mappedPlayer = this.modelMapper.map(playerSeedXmlDto, Player.class);

            String townName = playerSeedXmlDto.getTownName().getName();
            Town town = this.townRepository.findByName(townName);

            String name = playerSeedXmlDto.getTeamName().getName();
            Team teamByName = this.teamRepository.findTeamByName(name);


            Long id2 = playerSeedXmlDto.getStatId().getId();
            Stat stat = this.statRepository.findById(id2).orElse(null);

            mappedPlayer.setTeam(teamByName);
            mappedPlayer.setTown(town);
            mappedPlayer.setStat(stat);
            return mappedPlayer;
        }).collect(Collectors.toList());
        System.out.println();
        collect.stream().forEach(this.playerRepository::save);
        return sb.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<Player> players = this.playerRepository.OrderPlayerByShootingDescOrderThenByPassingInDescOrderThenByEnduranceDescOrderFinallyByPlayerLastName();
        players.forEach(player -> {

            String firstName = player.getFirstName();
            String lastName = player.getLastName();
            String teamName = player.getTeam().getName();
            String stadiumName = player.getTeam().getStadiumName();
            String position = player.getPosition().toString();
            String playerNames = String.format("Player - %s %s", firstName, lastName);
            sb.append(playerNames).append(System.lineSeparator());
            String positionOutput = String.format("   Position - %s", position);
            sb.append(positionOutput).append(System.lineSeparator());
            sb.append(String.format("   Team - %s", teamName)).append(System.lineSeparator());
            sb.append(String.format("   Stadium - %s", stadiumName)).append(System.lineSeparator());

        });
        return sb.toString().trim();
    }
}
