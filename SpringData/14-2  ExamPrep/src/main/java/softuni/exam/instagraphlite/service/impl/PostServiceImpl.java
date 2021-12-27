package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.xmls.PostSeedDto;
import softuni.exam.instagraphlite.models.dto.xmls.PostSeedRootDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.ProjectConstants;
import softuni.exam.instagraphlite.service.interfaces.PictureService;
import softuni.exam.instagraphlite.service.interfaces.PostService;
import softuni.exam.instagraphlite.service.interfaces.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.xmlparserfolder.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PostServiceImpl(PostRepository postRepository, UserService userService, PictureService pictureService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, XmlParser xmlParser1) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser1;
    }


    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        String postsInputFile = ProjectConstants.getPostsInputFile();
        return Files.readString(Path.of(postsInputFile));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        // за xml => в случая директорията, така е парсера:
        String s = ProjectConstants.getPostsInputFile();
        StringBuilder output = new StringBuilder();
        PostSeedRootDto postSeedRootDto = this.xmlParser.fromFile(s, PostSeedRootDto.class);
        postSeedRootDto
                .getPosts()
                .stream()
                .filter(postSeedDto -> {
                    return isPassedCheck(output, postSeedDto);
                })
                .map(postSeedDto -> {

                    Post mappedPost = this.modelMapper.map(postSeedDto, Post.class);
                    String username = postSeedDto.getUser().getUsername();

                    User userByUsername = this.userService.findUserByUsername(username);
                    if (userByUsername != null) {
                        mappedPost.setUser(userByUsername);
                    } else {
                        System.out.println("No such a user in database!");
                    }

                    Picture pictureByPath = this.pictureService.findPictureByPath(postSeedDto.getPicture().getPath());
                    if (pictureByPath != null) {
                        mappedPost.setPicture(pictureByPath);
                    } else {
                        System.out.println("No such a picture in database!");
                    }

                    return mappedPost;
                })
                .forEach(this.postRepository::save);

        return output.toString().toUpperCase(Locale.ROOT).trim();
    }

    private boolean isPassedCheck(StringBuilder output, PostSeedDto postSeedDto) {
        boolean isValid = this.validationUtil.isValid(postSeedDto);
        boolean userExists = this.userService
                .checkIfUserExistsInDataBase(postSeedDto.getUser().getUsername());
        boolean pictureExists = this.pictureService.checkIfPictureExistsInDataBase(postSeedDto.getPicture().getPath());
        boolean passedCheck = isValid && userExists && pictureExists;

        if (passedCheck) {
            Picture picture = this.pictureService.findPictureByPath(postSeedDto.getPicture().getPath());
            String username = postSeedDto.getUser().getUsername();
            String format = String.format("Successfully imported post, made by %s", username);
            output.append(format).append(System.lineSeparator());
        } else {
            output.append("Invalid post").append(System.lineSeparator());
        }
        return passedCheck;
    }
}
