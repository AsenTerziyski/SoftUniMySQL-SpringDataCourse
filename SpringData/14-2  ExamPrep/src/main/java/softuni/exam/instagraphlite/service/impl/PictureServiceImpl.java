package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.jsons.PictureSeedDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.ProjectConstants;
import softuni.exam.instagraphlite.service.interfaces.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        String pictureInputFile = ProjectConstants.getPictureInputFile();
        return Files.readString(Path.of(pictureInputFile));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder output = new StringBuilder();
        String inputInfo = readFromFileContent();
        if (areImported()) {
            return "Pictures are already imported!";
        }


        PictureSeedDto[] pictureSeedDtos = this.gson.fromJson(inputInfo, PictureSeedDto[].class);
        Arrays.stream(pictureSeedDtos).filter(pictureSeedDto -> {
            boolean isValid = this.validationUtil.isValid(pictureSeedDto);
            boolean entityExistsTrue = checkIfPictureExistsInDataBase(pictureSeedDto.getPath());
            double size = pictureSeedDto.getSize();
            String formated = String.format("Successfully imported Picture with size %.2f", size);
            if (isValid && !entityExistsTrue) {
                output.append(formated).append(System.lineSeparator());
            } else {
                output.append("Invalid picture").append(System.lineSeparator());
            }
            boolean passedCheck = (isValid && !entityExistsTrue);
            return passedCheck;
        })
                .map(pictureSeedDto -> this.modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(this.pictureRepository::save);


        return output.toString().toUpperCase(Locale.ROOT).trim();
    }

    @Override
    public boolean checkIfPictureExistsInDataBase(String path) {
//        1. Първи начин:
//        Picture byPath = this.pictureRepository.findByPath(path);
//        if (byPath == null) {
//            return false;
//        }
//        return true;

//        2. Втори начин:
        return this.pictureRepository
                .existsByPath(path);
    }

    @Override
    public String exportPictures() {
        double size = 30000.0;
        StringBuilder output = new StringBuilder();
        List<Picture> allBySizeGreaterThanOrderBySize = this.pictureRepository.findAllBySizeGreaterThanOrderBySize(size);
        allBySizeGreaterThanOrderBySize.forEach(picture -> {
            String format = String.format("%.2f - %s", picture.getSize(), picture.getPath());
            output.append(format).append(System.lineSeparator());
        });
        return output.toString().trim();
    }

    @Override
    public Picture findPictureByPath(String path) {
        return this.pictureRepository.findByPath(path).orElse(null);
    }
}
