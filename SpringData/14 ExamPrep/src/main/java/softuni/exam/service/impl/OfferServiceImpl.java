package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmldtos.CarIdDto;
import softuni.exam.models.dto.xmldtos.OfferSeedRootDto;
import softuni.exam.models.dto.xmldtos.SellerIdDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparserfolder.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    private final CarService carService;
    private final SellerService sellerService;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OfferSeedRootDto offerSeedRootDto = xmlParser.fromFile(OFFERS_FILE_PATH, OfferSeedRootDto.class);
        StringBuilder sb = new StringBuilder();
        offerSeedRootDto.getOffers().stream().filter(offerSeedDto -> {
            boolean offerIsValid = this.validationUtil.isValid(offerSeedDto);
            if (offerIsValid) {
                String date = offerSeedDto.getAddedOn().split("\\s+")[0];
                String time = offerSeedDto.getAddedOn().split("\\s+")[1];
                Boolean hasGoldStatus = offerSeedDto.getHasGoldStatus();
                sb
                        .append(String.format("Successfully import offer %sT%s - %s", date, time, hasGoldStatus))
                        .append(System.lineSeparator());
            } else {
                sb.append("Invalid offer").append(System.lineSeparator());
            }
            return offerIsValid;
        })
                .map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);

                    CarIdDto catIdDto = offerSeedDto.getCar();
                    Long carID = catIdDto.getId();
                    Car carById = this.carService.findCarById(carID);

                    SellerIdDto sellerIdDto = offerSeedDto.getSeller();
                    Long sellerId = sellerIdDto.getId();
                    Seller sellerById = this.sellerService.findSellerById(sellerId);

                    offer.setSeller(sellerById);
                    offer.setCar(carById);
                    return offer;
                })
                .forEach(this.offerRepository::save);


        String output = sb.toString().trim();
        return output;
    }

}
