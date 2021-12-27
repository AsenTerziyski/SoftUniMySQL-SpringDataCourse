package com.example.json_exrcs.service.impl;

import com.example.json_exrcs.constants.ProjectConstants;
import com.example.json_exrcs.model.dto.PR200.UserWithFriendsDto;
import com.example.json_exrcs.model.dto.UserSeedDto;
import com.example.json_exrcs.model.dto.UserSoldDto;
import com.example.json_exrcs.model.entity.User;
import com.example.json_exrcs.repository.UserRepository;
import com.example.json_exrcs.service.UserService;
import com.example.json_exrcs.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//import static com.example.json_exrcs.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private static final String usersFile = "users.json";
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        //short way:
//        if (this.userRepository.count() == 0) {
//
//            Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH + usersFile)),
//                    UserSeedDto[].class))
//                    .filter(validationUtil::isValid)
//                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
//                    .forEach(userRepository::save);
//            System.out.println("User data seeded!");
//
//        }
        if (this.userRepository.count() > 0) {
            System.out.println("*********************************Category data already seeded*********************************");
            return;
        }

        //read
        String resourcesFilePath = ProjectConstants.USERS_FILE_FULL_PATH;
        String usersFileContent = Files.readString(Path.of(resourcesFilePath));

        UserSeedDto[] userSeedDtos = this.gson.fromJson(usersFileContent, UserSeedDto[].class);
        //стриймваме колекциата от дто, мапваме я към юзър, и послед всеки юзър го сейваме в базата:
        Arrays.stream(userSeedDtos).filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
        System.out.println("*********************************User data seeded*********************************");
    }

    @Override
    public User findRandomUser() {
        long randomUserId = ThreadLocalRandom
                .current()
                .nextLong(1, this.userRepository.count() + 1);

        return this.userRepository.findById(randomUserId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWhoSoldMoreThanProduct() {

        List<UserSoldDto> collect = userRepository.findAllByUsersWithMoreThanOneSoldProductOrderByLastNameThenByFirstName()
                .stream().map(user -> modelMapper.map(user, UserSoldDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Modifying
    @Override
    public void setRandomFriendsOfUsers() {

        long usersCount = this.userRepository.count();
        int randomIntBig = ThreadLocalRandom.current().nextInt(1, 10);

        long d = 0;
        while (d < 10) {
            Set<User> randomFriends = new HashSet<>();
            int randomInt = ThreadLocalRandom.current().nextInt(1, 10);
            for (int k = 0; k < randomInt; k++) {
                Long randomId = ThreadLocalRandom.current().nextLong(1, usersCount + 1);
                User randomFriend = this.userRepository.findById(randomId).orElse(null);
                if (randomFriend != null) {
                    randomFriends.add(randomFriend);
                }
            }

            Long randomId = ThreadLocalRandom.current().nextLong(1, 3);
            User randomUser = this.userRepository.findById(randomId).orElse(null);
            if (randomUser != null) {
                randomUser.setFriends(randomFriends);
                this.userRepository.save(randomUser);
            }

            d++;
        }
    }

    @Override
    public List<UserWithFriendsDto> findAllUsersWithFriends() {
        List<User> usersWithFriends = this.userRepository.findAllByFriendsIsNotNull();
        List<UserWithFriendsDto> collect = usersWithFriends.stream()
                .map(user -> {
                    UserWithFriendsDto map = this.modelMapper.map(user, UserWithFriendsDto.class);
                    map.setLastName(user.getLastName());
                    return map;
                })
                .collect(Collectors.toList());
        System.out.println();

        return collect;
    }

}
