package softuni.exam.instagraphlite.service.interfaces;

import softuni.exam.instagraphlite.models.entities.Picture;

import java.io.IOException;
import java.util.Optional;

public interface PictureService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importPictures() throws IOException;

    boolean checkIfPictureExistsInDataBase(String path);

    String exportPictures();

    Picture findPictureByPath(String path);

}
