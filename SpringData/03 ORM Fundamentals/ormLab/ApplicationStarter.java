package ormLab;

import ormFramework.core.EntityManager;
import ormFramework.core.EntityManagerFactory;
import ormLab.entity.Address;
import ormLab.entity.Department;
import ormLab.entity.Employee;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ApplicationStarter {
    public static void main(String[] args) throws SQLException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        System.out.println("TestTest");
        EntityManager entityManager = EntityManagerFactory.create(
                "mysql",
                "localhost",
                3306,
                "root",
                "M@lmsuite3996",
                "test_orm",
                ApplicationStarter.class
        );

        Employee employee = entityManager.findById(25, Employee.class);
        System.out.println(employee.getEmployeeId());
        System.out.println(employee.getSalary());

        Department dep = entityManager.findById(1, Department.class);
        System.out.println(dep.getName());
        System.out.println(dep.getId());

        Address address = entityManager.findById(2, Address.class);
        System.out.println(address.getId());
        System.out.println(address.getStreet());
        System.out.println(address.getStreetNumber());
        System.out.println(address.getPeopleCount());


    }
}
