package com.example.football.service.impl;

import com.example.football.models.dto.jsondtos.TeamsSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_FILE_PATH = "src/main/resources/files/json/teams.json";

    private final TeamRepository teamRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public TeamServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder outputSb = new StringBuilder();
        TeamsSeedDto[] teamsSeedDtos = this.gson.fromJson(readTeamsFileContent(), TeamsSeedDto[].class);
        List<TeamsSeedDto> validTeams = Arrays.stream(teamsSeedDtos).filter(teamsSeedDto -> {
            boolean validTeam = this.validationUtil.isValid(teamsSeedDto);
            if (validTeam) {
                if (teamsSeedDto.getFanBase()>=1000) {
                    String outputStr = String.format("Successfully imported %s - %d", teamsSeedDto.getName(), teamsSeedDto.getFanBase());
                    outputSb.append(outputStr).append(System.lineSeparator());
                }
            } else {
                outputSb.append("Invalid Team").append(System.lineSeparator());
            }
            return validTeam;
        }).collect(Collectors.toList());
        validTeams.stream()
                .map(teamsSeedDto -> {
                    Team mappedTeam = modelMapper.map(teamsSeedDto, Team.class);
                    Town byName = this.townRepository.findByName(teamsSeedDto.getTownName());
                    mappedTeam.setTown(byName);
                    return mappedTeam;
                })
                .forEach(this.teamRepository::save);
        return outputSb.toString().trim();
    }
}
