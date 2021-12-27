package softuni.exam.instagraphlite.service.interfaces;

import softuni.exam.instagraphlite.models.entities.User;

import java.io.IOException;

public interface UserService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importUsers() throws IOException;

    boolean checkIfUserExistsInDataBase(String username);

    String exportUsersWithTheirPosts();

    User findUserByUsername(String username);

}
