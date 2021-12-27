package com.example.objectmapping.services;

import com.example.objectmapping.config.ModelMapperConfig;
import com.example.objectmapping.models.dto.EmployeeDto;
import com.example.objectmapping.models.dto.ManagerDto;
import com.example.objectmapping.models.entities.Employee;
import com.example.objectmapping.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapperConfig modelMapperConfig;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapperConfig modelMapperConfig) {
        this.employeeRepository = employeeRepository;
        this.modelMapperConfig = modelMapperConfig;
    }

    @Override
    public EmployeeDto findOne(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Employee employee = this
                .employeeRepository
                .findById(id)
                .orElseThrow();

        // ръчно мапване
//        EmployeeDto dto = new EmployeeDto();
//        dto.setFirstName(employee.getFirstName());
//        dto.setLastName(employee.getLastName());
//        dto.setSalary(employee.getSalary());
//        return dto;
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public ManagerDto findOneManagerDto(Long id) {
//        ModelMapper mapper = new ModelMapper();
//        return mapper.map(this.employeeRepository.findById(id).orElseThrow(), ManagerDto.class);
        ModelMapper mapper1 = this.modelMapperConfig.modelMapper();
        return mapper1.map(this.employeeRepository.findById(id).orElseThrow(), ManagerDto.class);
    }

    @Override
    public List<ManagerDto> findAllManagers() {
//        ModelMapper mapper = new ModelMapper();
//        mapper.addMappings(new PropertyMap<Employee, EmployeeDto>() {
//            @Override
//            protected void configure() {
//                map().setSalary(source.getSalary());
//            }
//        });
//        return mapper.map(this.employeeRepository.findAll(), new TypeToken<List<ManagerDto>>(){}.getType());
        ModelMapper mapper = this.modelMapperConfig.modelMapper();
        return mapper.map(this.employeeRepository.findAll(), new TypeToken<List<ManagerDto>>(){}.getType());
    }

}
