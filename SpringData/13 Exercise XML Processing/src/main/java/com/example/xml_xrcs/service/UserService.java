package com.example.xml_xrcs.service;

import com.example.xml_xrcs.model.dto.UserSeedDto;
import com.example.xml_xrcs.model.entity.User;

import java.util.List;

public interface UserService {
    long getUserEntityCount();


    void seedUsers(List<UserSeedDto> users);
    User getRandomUser();
}
