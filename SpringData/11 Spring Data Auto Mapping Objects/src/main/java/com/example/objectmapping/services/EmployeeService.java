package com.example.objectmapping.services;

import com.example.objectmapping.models.dto.EmployeeDto;
import com.example.objectmapping.models.dto.ManagerDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto findOne(Long id);
    ManagerDto findOneManagerDto(Long id);
    List<ManagerDto> findAllManagers();
}
