package com.example.objectmapping;

import com.example.objectmapping.models.dto.EmployeeDto;
import com.example.objectmapping.models.dto.ManagerDto;
import com.example.objectmapping.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
//ako ne dam @Component nyama da se izpalni!!!
public class CommLineRunner implements CommandLineRunner {

    private EmployeeService employeeService;

    public CommLineRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        test1(scanner);
        test2(scanner);
        test3();
    }

    private void test3() {
        System.out.println("test3");
        System.out.println();
        List<ManagerDto> managers = this.employeeService.findAllManagers();
        managers.stream().
                forEach(manager-> System.out.println(manager.getFirstName() + manager.getLastName()));
    }

    private void test2(Scanner scanner) {
        System.out.println("test2");
        System.out.print("Please enter valid ID: ");
        Long inputId = Long.parseLong(scanner.nextLine());
        ManagerDto oneManagerDto = this.employeeService.findOneManagerDto(inputId);
        System.out.println(oneManagerDto.getFirstName() + " has subordinates:");
        oneManagerDto
                .getSubordinates()
                .stream()
                .forEach(employeeDto -> {
                    System.out.println("    =>" + employeeDto.getFirstName()
                            + " "
                            + employeeDto.getLastName()
                            + " with salary: "
                            + employeeDto.getSalary());
                });
    }

    private void test1(Scanner scanner) {
        System.out.println("test1");
        System.out.print("Please enter valid ID: ");
        Long inputId = Long.parseLong(scanner.nextLine());
        EmployeeDto one = this.employeeService.findOne(inputId);
        System.out.println(one.getFirstName() + " has salary: " + one.getSalary() + "BGN");
    }
}
