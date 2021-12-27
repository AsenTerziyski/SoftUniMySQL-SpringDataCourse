package softuni.exam.instagraphlite.util.xmlparserfolder;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    // дженерик метод <Т>, който връща Т, името на метода (....приема ....) :
    <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException;
}
