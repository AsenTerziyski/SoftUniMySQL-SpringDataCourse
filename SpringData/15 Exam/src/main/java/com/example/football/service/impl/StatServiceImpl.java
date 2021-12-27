package com.example.football.service.impl;

import com.example.football.models.dto.xmldtos.StatsSeedXmlRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.xmlparserfolder.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private static final String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder outputSb = new StringBuilder();
        StatsSeedXmlRootDto statsSeedXmlRootDto = this.xmlParser.fromFile(STATS_FILE_PATH, StatsSeedXmlRootDto.class);
        statsSeedXmlRootDto.getStats().stream().filter(statSeedDto -> {
            boolean validStat = this.validationUtil.isValid(statSeedDto);
            if (validStat) {
                String output = String.format("Successfully imported Stat %.2f - %.2f - %.2f", statSeedDto.getPassing(), statSeedDto.getShooting(), statSeedDto.getEndurance());
                outputSb.append(output).append(System.lineSeparator());
            } else {
                outputSb.append("Invalid Stat").append(System.lineSeparator());
            }

            return validStat;
        }).map(statSeedDto -> this.modelMapper.map(statSeedDto, Stat.class)).forEach(this.statRepository::save);

        return outputSb.toString().trim();
    }
}
