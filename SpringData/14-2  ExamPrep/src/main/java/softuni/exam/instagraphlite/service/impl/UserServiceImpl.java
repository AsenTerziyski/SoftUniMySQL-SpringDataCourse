package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.jsons.UsersSeedDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.ProjectConstants;
import softuni.exam.instagraphlite.service.interfaces.PictureService;
import softuni.exam.instagraphlite.service.interfaces.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository,
                           PictureService pictureService,
                           ModelMapper modelMapper,
                           Gson gson,
                           ValidationUtil validationUtil) {

        this.userRepository = userRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        String usersInputFile = ProjectConstants.getUsersInputFile();
        return Files.readString(Path.of(usersInputFile));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder output = new StringBuilder();
        UsersSeedDto[] usersSeedDtos = this.gson.fromJson(readFromFileContent(), UsersSeedDto[].class);
        Arrays.stream(usersSeedDtos).filter(usersSeedDto -> {
            boolean isValid = this.validationUtil.isValid(usersSeedDto);
            boolean pictureExistsInDataBase = this.pictureService.checkIfPictureExistsInDataBase(usersSeedDto.getProfilePicture());
            boolean userExists = checkIfUserExistsInDataBase(usersSeedDto.getUsername());

            boolean checkedUser = (isValid && pictureExistsInDataBase && !userExists);
            if (checkedUser) {
                String format = String.format("Successfully imported user: %s", usersSeedDto.getUsername());
                output.append(format).append(System.lineSeparator());
            } else {
                output.append("Invalid user!").append(System.lineSeparator());
            }
            return checkedUser;
        })
                .map(usersSeedDto -> {
                    User mappedUser = this.modelMapper.map(usersSeedDto, User.class);
                    Picture pictureByPath = this.pictureService.findPictureByPath(usersSeedDto.getProfilePicture());
                    if (pictureByPath != null) {
                        mappedUser.setProfilePicture(pictureByPath);
                    }
                    return mappedUser;
                })
                .forEach(this.userRepository::save);
        return output.toString().toUpperCase(Locale.ROOT).trim();
    }

    @Override
    public boolean checkIfUserExistsInDataBase(String username) {
        boolean exists = this.userRepository.existsByUsername(username);
        if (exists) {
            return true;
        }
        return false;
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder output = new StringBuilder();
        List<User> allUsersByPostCountDescThenByUserId = this.userRepository.findAllUsersByPostCountDescThenByUserId();
        allUsersByPostCountDescThenByUserId
                .forEach(user -> {
                    output.append("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").append(System.lineSeparator());

                    String username = user.getUsername();
                    int size = user.getPosts().size();
                    output.append(String.format("User %s", username))
                            .append(System.lineSeparator());
                    output.append(String.format("Post count: %d", size))
                            .append(System.lineSeparator());

                    output.append("Post details:")
                            .append(System.lineSeparator());

                    user
                            .getPosts()
                            .stream()
                            .sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                            .forEach(post -> {
                                String caption = post.getCaption();
                                output.append(String.format("   Caption: %s", caption))
                                        .append(System.lineSeparator());

                                double pictureSize = post.getPicture().getSize();
                                output.append(String.format("   Picture size: %.2f", pictureSize))
                                        .append(System.lineSeparator());
                            });
                });

        return output.toString().toUpperCase(Locale.ROOT).trim();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }
}
