package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonsdtos.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    //prsf =>shortCut
    private static final String CARS_FILE_PATH = "src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        //тук връщам дали carRepository.count > 0!!!
        //ako count = 0 => isImported = false => areImported = false! и обратното!
        boolean isImported = this.carRepository.count() > 0;
        return isImported;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        String string = Files.readString(Path.of(CARS_FILE_PATH));
        return string;
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<Car> validCars = Arrays
                .stream(this.gson.fromJson(readCarsFileContent(), CarSeedDto[].class))
                .filter(carSeedDto -> {
                    boolean isValid = this.validationUtil.isValid(carSeedDto);
                    if (!isValid) {
                        sb.append("Invalid car").append(System.lineSeparator());
                    } else {
                        String make = carSeedDto.getMake();
                        String model = carSeedDto.getModel();
                        String ouput = String.format("Successfully imported car - %s - %s", make, model);
                        sb.append(ouput).append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(carSeedDto -> this.modelMapper.map(carSeedDto, Car.class))
                .collect(Collectors.toList());

        validCars.forEach(this.carRepository::save);

        String output = sb.toString().trim();
        return output;
    }


    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        List<Car> carsOrderByPicturesCountThenByMake = this.carRepository.findCarsOrderByPicturesCountThenByMake();
        carsOrderByPicturesCountThenByMake.stream().forEach(car -> {
            String make = car.getMake();
            String model = car.getModel();
            Integer kilometers = car.getKilometers();
            LocalDate registeredOn = car.getRegisteredOn();
            int numberOfPictures = car.getPictures().size();
            sb
                    .append(String.format("Car make - %s, model - %s\n" +
                    "\tKilometers - %d\n" +
                    "\tRegistered on - %s\n" +
                    "\tNumber of pictures - %d\n", make, model, kilometers, registeredOn, numberOfPictures))
                    .append(System.lineSeparator());
        });
        return sb.toString().trim();
    }

    @Override
    public Car findCarById(Long carId) {
        Car car = this.carRepository.findById(carId).orElse(null);
        return car;
    }
}
