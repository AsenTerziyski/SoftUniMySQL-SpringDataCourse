package softuni.exam.models.dto.jsonsdtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CarSeedDto {

    //анотации за гсон => @Експоуз
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Integer kilometers;
    //в дто-то вземаме датата да е Стринг:
    @Expose
    private String registeredOn;

    // в ДТО-то правим "тежката валидация"

    // мин и макс са включително!
    @Size(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Size(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // за позитивни числа слагаме @Позитив:
    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
