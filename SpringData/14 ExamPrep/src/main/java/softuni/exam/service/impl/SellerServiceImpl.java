package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmldtos.SellerSeedRootDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparserfolder.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String SELLERS_FILE_PATH = "src/main/resources/files/xml/sellers.xml";

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    //добавям xml parser:
    private final XmlParser xmlParser;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        boolean sellersAreImported = this.sellerRepository.count() > 0;
        return sellersAreImported;
    }

    @Override
    public String readSellersFromFile() throws IOException {

        return Files.readString(Path.of(SELLERS_FILE_PATH));
    }


    @Override
    public String importSellers() throws IOException, JAXBException {
        SellerSeedRootDto sellerSeedRootDto = xmlParser.fromFile(SELLERS_FILE_PATH, SellerSeedRootDto.class);
        StringBuilder sb = new StringBuilder();
        sellerSeedRootDto.getSellers().stream().filter(sellerSeedDto -> {
            boolean sellerIsValid = this.validationUtil.isValid(sellerSeedDto);
            if (sellerIsValid) {
                sb.append("Successfully import seller " + sellerSeedDto.getLastName() + "-" + sellerSeedDto.getEmail()).append(System.lineSeparator());
            } else {
                sb.append("Invalid seller").append(System.lineSeparator());
            }
            return sellerIsValid;
        })
                .map(sellerSeedDto -> this.modelMapper.map(sellerSeedDto, Seller.class))
                .forEach(this.sellerRepository::save);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxSELLERS IMPORTEDxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        String output = sb.toString().trim();
        return output;
    }

    @Override
    public Seller findSellerById(Long sellerId) {
        Seller seller = this.sellerRepository.findById(sellerId).orElse(null);
        return seller;
    }
}
