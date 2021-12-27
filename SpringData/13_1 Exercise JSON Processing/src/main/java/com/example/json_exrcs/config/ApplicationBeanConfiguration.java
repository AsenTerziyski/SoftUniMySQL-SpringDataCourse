package com.example.json_exrcs.config;

import com.example.json_exrcs.model.dto.PR200.UserWithFriendsDto;
import com.example.json_exrcs.model.dto.PR200.UsersFriendDto;
import com.example.json_exrcs.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new BufferedReader(new InputStreamReader(System.in)));
    }
}
