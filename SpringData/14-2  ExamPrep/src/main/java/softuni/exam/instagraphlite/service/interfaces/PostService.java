package softuni.exam.instagraphlite.service.interfaces;

import javax.xml.bind.JAXBException;
import java.io.IOException;


public interface PostService {
    boolean areImported();
    String readFromFileContent() throws IOException;
    String importPosts() throws IOException, JAXBException;

}
