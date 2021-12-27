package com.example.spdamo.service;

import com.example.spdamo.model.dto.UserLoginDto;
import com.example.spdamo.model.dto.UserRegisterDto;
import com.example.spdamo.model.entity.User;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    boolean hasLoggedUser();

    User getLoggedUser();
}
