package com.example.xml_xrcs.service.impl;

import com.example.xml_xrcs.model.dto.UserSeedDto;
import com.example.xml_xrcs.model.entity.User;
import com.example.xml_xrcs.repository.UserRepository;
import com.example.xml_xrcs.service.UserService;
import com.example.xml_xrcs.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getUserEntityCount() {
        return this.userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users.stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(this.userRepository::save);
        System.out.println("************************************************************************************************************************************************************************");
        System.out.println("Successfully seeded USERS.");
        System.out.println("************************************************************************************************************************************************************************");
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1L, getUserEntityCount() + 1L);
        User resultUser = this.userRepository
                .findById(randomId)
                .orElse(null);
        return resultUser;
    }

}



