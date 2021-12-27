package com.example.football.service.impl;

import com.example.football.models.dto.jsondtos.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
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
public class TownServiceImpl implements TownService {

    private static final String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder outputSb = new StringBuilder();
        TownSeedDto[] townSeedDtos = this.gson.fromJson(readTownsFileContent(), TownSeedDto[].class);
        System.out.println();
        List<Town> validatedTowns = Arrays.stream(townSeedDtos)
                .filter(townSeedDto -> {
                    boolean validTown = this.validationUtil.isValid(townSeedDto);
                    if (validTown) {
                        Town townByName = findTownByName(townSeedDto.getName());
//                        if (townByName == null) {
//                            String outputStr = String.format("Successfully imported Town %s - %d", townSeedDto.getName(), townSeedDto.getPopulation());
//                            outputSb.append(outputStr).append(System.lineSeparator());
//                        }
                        String outputStr = String.format("Successfully imported Town %s - %d", townSeedDto.getName(), townSeedDto.getPopulation());
                        outputSb.append(outputStr).append(System.lineSeparator());
                    } else {
                        outputSb.append("Invalid Town").append(System.lineSeparator());
                    }

                    return validTown;
                })
                .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class)).collect(Collectors.toList());
        validatedTowns.forEach(this.townRepository::save);
        return outputSb.toString().trim();
    }
    @Override
    public Town findTownByName(String name) {
        return this.townRepository.findByName(name);
    }
}
