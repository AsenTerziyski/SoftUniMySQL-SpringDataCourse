package com.example.json_exrcs.service;

import com.example.json_exrcs.model.dto.PR200.UserWithFriendsDto;
import com.example.json_exrcs.model.dto.UserSoldDto;
import com.example.json_exrcs.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;
    User findRandomUser();


    List<UserSoldDto> findAllUsersWhoSoldMoreThanProduct();

    void setRandomFriendsOfUsers();

    List<UserWithFriendsDto> findAllUsersWithFriends();
//
//    List<UsersWithFriendsDto> findUsersWithFriends();
}
