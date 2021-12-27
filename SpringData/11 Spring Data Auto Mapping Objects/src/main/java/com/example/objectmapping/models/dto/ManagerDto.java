package com.example.objectmapping.models.dto;

import java.util.ArrayList;
import java.util.List;

public class ManagerDto extends BasicEmployeeDto {
    private List<EmployeeDto> subordinates;

    public ManagerDto(List<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }

    public ManagerDto() {
        this.subordinates = new ArrayList<>();
    }

    public List<EmployeeDto> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }
}
