package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonsdtos.PictureSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    public static final String PICTURES_FILE_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        boolean picturesAreImported = this.pictureRepository.count() > 0;
        return picturesAreImported;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        PictureSeedDto[] pictureSeedDtos = gson.fromJson(readPicturesFromFile(), PictureSeedDto[].class);
        System.out.println();
        StringBuilder sb = new StringBuilder();
        List<Picture> validPictures = Arrays.stream(pictureSeedDtos)
                .filter(pictureSeedDto ->

                {
                    boolean pictureIsValid = this.validationUtil.isValid(pictureSeedDto);
                    String pictureName = pictureSeedDto.getName();
                    if (pictureIsValid) {
                        sb.append("Successfully import picture " + pictureName).append(System.lineSeparator());
                    } else {
                        sb.append("Invalid picture").append(System.lineSeparator());
                    }
                    return pictureIsValid;
                })
                .map(pictureSeedDto ->
                {
                    Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);
                    Long carId = pictureSeedDto.getCar();
                    Car car = this.carService.findCarById(carId);
                    picture.setCar(car);
                    return picture;
                })
                .collect(Collectors.toList());

        validPictures.forEach(this.pictureRepository::save);

        String pictureOutput = sb.toString().trim();
        return pictureOutput;

    }
}
