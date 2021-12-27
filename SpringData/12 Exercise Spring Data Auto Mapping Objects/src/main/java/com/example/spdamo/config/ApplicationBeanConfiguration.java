package com.example.spdamo.config;

import com.example.spdamo.model.dto.GameAddDto;
import com.example.spdamo.model.entity.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//mnogo vazno da e notatsiya @Configuration
@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        // 0. mapper:
        ModelMapper modelMapper = new ModelMapper();
        // 1. converter should always be before mapper:
        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern("dd-MM-yyyy")
                        .withLocale(Locale.ENGLISH);
//              LocalDate parse = LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate parse = LocalDate.parse(mappingContext.getSource(), formatter);
                return parse;
            }
        };
        // 2. adding converter:
        modelMapper.addConverter(localDateConverter);
        // 3. then create typeMap:
        modelMapper
                .createTypeMap(GameAddDto.class, Game.class)
                .addMappings(mapper -> mapper.map(GameAddDto::getThumbnailURL, Game::setImageThumbnail));
        return modelMapper;
    }
}
