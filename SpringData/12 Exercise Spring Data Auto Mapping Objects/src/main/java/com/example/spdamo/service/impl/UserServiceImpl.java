package com.example.spdamo.service.impl;


import com.example.spdamo.model.dto.UserLoginDto;
import com.example.spdamo.model.dto.UserRegisterDto;
import com.example.spdamo.model.entity.User;
import com.example.spdamo.repository.UserRepository;
import com.example.spdamo.service.UserService;
import com.example.spdamo.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {


        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Please confirm password again.");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.getViolation(userRegisterDto);
        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(v -> v.getMessage())
                    .forEach(System.out::println);
            return;
        }


        User user = this.modelMapper.map(userRegisterDto, User.class);
//        Optional<User> all = this.userRepository.getAll();
//        if (all.stream().count() == 0L) {
//            user.setAdmin(true);
//        }
        Optional<User> byEmailAndPassword = this.userRepository.findByEmailAndPassword(userRegisterDto.getEmail(), userRegisterDto.getPassword());
        if (byEmailAndPassword.isPresent()) {
            return;
        }
        userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violation = validationUtil.getViolation(userLoginDto);
        if (!violation.isEmpty()) {
            violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = this.userRepository
                .findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        if (this.loggedInUser == null) {
            if (userLoginDto.getEmail().equals(user.getEmail()) && userLoginDto.getPassword().equals(user.getPassword())) {
                this.loggedInUser = user;
                System.out.printf("Successfully logged in %s", user.getFullName());
                System.out.println();
            }
        } else {
            if (loggedInUser.getPassword().equals(user.getPassword())
                    && loggedInUser.getEmail().equals(user.getEmail())) {
                System.out.println("User " + loggedInUser.getFullName() + " already logged in!");
            }
        }

    }

    @Override
    public void logout() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user is logged in.");
        } else {
            System.out.printf("Successfully logged out user with name: %s.", loggedInUser.getFullName());
            System.out.println();
            loggedInUser = null;
        }
    }

    @Override
    public boolean hasLoggedUser() {
        if (loggedInUser == null) {
            return false;
        }
        return true;
    }

    @Override
    public User getLoggedUser() {
        return this.loggedInUser;
    }
}
