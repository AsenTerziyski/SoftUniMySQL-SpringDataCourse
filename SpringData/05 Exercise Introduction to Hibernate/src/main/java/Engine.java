import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        System.out.println("Select exercise number:");
        try {
            int exerciseNumber = Integer.parseInt(bufferedReader.readLine());
            switch (exerciseNumber) {
                case 2:
                    solveProblemTwo();
                    break;
                case 3:
                    solveProblemThree();
                    break;
                case 4:
                    solveProblemFour();
                    break;
                case 5:
                    solveProblemFive();
                    break;
                case 6:
                    solveProblemSix();
                    break;
                case 7:
                    solveProblemSeven();
                    break;
                case 8:
                    solveProblemEight();
                    break;
                case 9:
                    solveProblemNine();
                    break;
                case 10:
                    solveProblemTen();
                    break;
                case 11:
                    solveProblemEleven();
                    break;
                case 12:
                    solveProblemTwelve();
                    break;
                case 13:
                    solveProblemThirteen();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void solveProblemThirteen() throws IOException {
        System.out.println("Enter town name:");
        String townName = bufferedReader.readLine();
        Town town = entityManager
                .createQuery("select t from Town t where t.name = :tn", Town.class)
                .setParameter("tn", townName)
                .getSingleResult();

        int affected = removeAddressByTownId(town.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.println(String.format("%d address in %s is deleted", affected, townName));
    }

    private int removeAddressByTownId(Integer id) {

        List<Address> addresses = entityManager
                .createQuery("select a from Address a where a.town.id = :p_id", Address.class)
                .setParameter("p_id", id)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

        return addresses.size();
    }

    private void solveProblemTwelve() {
        List<Object[]> rows = entityManager
                .createNativeQuery("select d.name, max(e.salary) as max_salary " +
                        "from departments as d\n" +
                        "join employees as e\n" +
                        "on d.department_id = e.department_id\n" +
                        "group by d.name\n" +
                        "having max_salary not between 30000 and 70000")
                .getResultList();

        for (int i = 0; i < rows.size(); i++) {
            Object[] objects = rows.get(i);
            Object department = objects[0];
            String simpleName = department.getClass().getName().toString();
            Object salary = objects[1];
            System.out.println(department + " " + salary);
        }
    }

    private void solveProblemEleven() throws IOException {
        System.out.println("Enter the pattern:");
        String inputPattern = bufferedReader.readLine();
        String pattern = inputPattern + "%";
        List<Employee> employeeList = entityManager
                .createQuery("select e from Employee e where e.firstName like :n", Employee.class)
                .setParameter("n", pattern)
                .getResultList();
        for (int i = 0; i < employeeList.size(); i++) {
            String firstName = employeeList.get(i).getFirstName();
            String lastName = employeeList.get(i).getLastName();
            String jobTitle = employeeList.get(i).getJobTitle();
            BigDecimal salary = employeeList.get(i).getSalary();
            System.out.println(String.format("%s %s - %s - ($%.2f)",
                    firstName, lastName, jobTitle, salary));
        }
    }

    private void solveProblemTen() {
        entityManager.getTransaction().begin();

        int affected = entityManager
                .createQuery("update Employee e " +
                        "set e.salary = e.salary * 1.2" +
                        " where e.department.id in :dep_ids")
                .setParameter("dep_ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();

        List<Employee> employeesWithIncreasedSalaries = new ArrayList<>();
        if (affected == 0) {
            System.out.println("No salaries were increased. Please enter valid departments ids");
//            throw new IllegalArgumentException("No salaries were increased. Input departments ID don't exist.");
        } else {
            employeesWithIncreasedSalaries = entityManager.createQuery("select e from Employee e where e.department.id in :dep_ids", Employee.class).
                    setParameter("dep_ids", Set.of(1, 2, 4, 11)).getResultList();
        }

        for (int i = 0; i < employeesWithIncreasedSalaries.size(); i++) {
            Integer id = employeesWithIncreasedSalaries.get(i).getDepartment().getId();
            String firstName = employeesWithIncreasedSalaries.get(i).getFirstName();
            String lastName = employeesWithIncreasedSalaries.get(i).getLastName();
            BigDecimal salary = employeesWithIncreasedSalaries.get(i).getSalary();
//            if ((id == 1) || (id == 2) || (id == 4) || (id == 11)) {
//                System.out.println(String.format("%s %s ($%.2f)", firstName,
//                        lastName,salary));
//            }
            System.out.println(String.format("%s %s ($%.2f)", firstName, lastName, salary));
        }
    }

    private void solveProblemNine() {

        List<Project> projects = entityManager
                .createQuery("select p from Project as p order by p.startDate desc", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects
                .stream()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .forEach(project -> {
                    String projectName = project.getName();
                    String projectDescription = project.getDescription();
                    LocalDateTime startDate = project.getStartDate();
                    LocalDateTime endDate = project.getEndDate();
                    System.out.println("Project name: " + projectName);
                    System.out.println("    " + "Project Description: " + projectDescription);
                    System.out.println("    " + "Project Start Date:" + startDate);
                    if (endDate != null) {
                        System.out.println("    " + "Project End Date:" + endDate);
                    } else {
                        System.out.println("    " + "Project End Date N/A");
                    }
                });
    }

    private void solveProblemEight() throws IOException {
        System.out.println("Enter employee's ID:");
        int id = Integer.parseInt(bufferedReader.readLine());
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            System.out.println(String.format("There is no employee with ID:%d! Try again.", id));
            return;
        }
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String jobTitle = employee.getJobTitle();

        System.out.println(String.format("%s %s - %s", firstName, lastName, jobTitle));

        Set<Project> projects = employee.getProjects();
        if (projects.size() == 0) {
            System.out.println(String.format("This employee has no projects"));
            return;
        }
        projects
                .stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .forEach(p -> System.out.println(String.format("\t%s", p.getName())));
    }

    private void solveProblemSeven() {
//        List<Address> resultList = entityManager
//                .createQuery("select a from Address a order by a.employees.size desc", Address.class)
//                .getResultList();
        List<Address> resultList = entityManager
                .createQuery("select a from Address a order by a.employees.size desc", Address.class)
                .setMaxResults(10)
                .getResultList();

//        long count = 0;
        for (Address address : resultList) {
//            count++;
            if (address.getTown() == null) {
                System.out.println(String.format("%s Unknown town - %d",
                        address.getText(),
                        address.getEmployees().size()));
                continue;
            }
            System.out.println(String.format("%s %s - %d", address.getText(),
                    address.getTown().getName(),
                    address.getEmployees().size()));
//            if (count == 10) {
//                return;
//            }
        }
    }

    private void solveProblemSix() throws IOException {
        System.out.println("Enter employee's last name:");
        String lastName = bufferedReader.readLine();

        // 1. намирам емплоито по ласт нейм и му давам сингъл резълт:
        Employee employee = entityManager.createQuery("select e from Employee e where e.lastName = :ln", Employee.class)
                .setParameter("ln", lastName)
                .getSingleResult();

        // 2. правим нов адрес и го записваме в ДБ:
        Address newAddress = createNewAddress("Vitoshka 15");

        // 3. с нова транзакция сетвам адреса да е на намереното емплои:
        entityManager.getTransaction().begin();
        employee.setAddress(newAddress);
        entityManager.getTransaction().commit();
    }

    private Address createNewAddress(String addrss) {

        Address address = new Address();
        address.setText(addrss);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void solveProblemFive() {
        entityManager
                .createQuery("select e from Employee as e where e.department.name = :dn order by e.salary asc , e.id asc ", Employee.class)
                .setParameter("dn", "Research and Development")
                .getResultStream()
                .forEach(employee ->
                {
                    String firstName = employee.getFirstName();
                    String lastName = employee.getLastName();
                    String depName = employee.getDepartment().getName();
                    BigDecimal salary = employee.getSalary();
                    System.out.printf("%s %s %s --->>>> $%.2f;%n", firstName, lastName, depName, salary);
                });

//        entityManager
//                .createQuery("select e from Employee e " +
//                                "where e.department.name = :dn " +
//                                "order by e.salary asc , e.id"
//                        , Employee.class)
//                .setParameter("dn", "Research and Development")
//                .getResultStream()
//                .forEach
//                        (
//                                employee -> System.out.println(String.format("%s %s from %s - $%.2f",
//                                        employee.getFirstName(),
//                                        employee.getLastName(),
//                                        employee.getDepartment().getName(),
//                                        employee.getSalary()))
//                        );
    }

    private void solveProblemFour() {
        entityManager
                .createQuery("select e from Employee as e where e.salary>:sal", Employee.class)
                .setParameter("sal", BigDecimal.valueOf(50000L))
                .getResultStream()
                .forEach(employee -> System.out.println(employee.getFirstName() +
                        " " + employee.getLastName() +
                        " => " + employee.getSalary()));

//        List<Employee> resultEmployeeList = entityManager.
//                createQuery("select e from Employee e where e.salary >=:s", Employee.class).
//                setParameter("s", BigDecimal.valueOf(50000L)).
//                getResultList();
//        for (Employee employee : resultEmployeeList) {
//            System.out.println(employee.getFirstName());
//        }

//        entityManager
//                .createQuery("select e from Employee e where e.salary > :sal", Employee.class)
//                .setParameter("sal", BigDecimal.valueOf(50000L))
//                .getResultStream()
//                .map(Employee :: getFirstName)
//                .forEach(System.out::println);

//        entityManager
//                .createQuery("select e from Employee e where e.salary>:sal", Employee.class)
//                .setParameter("sal", BigDecimal.valueOf(50000L))
//                .getResultStream()
//                .forEach(employee -> System.out.println(employee.getFirstName()));
    }

    private void solveProblemThree() throws IOException {
        System.out.println("Enter employee's full name");
        String[] fullName = bufferedReader.readLine().split("\\s+");

        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("select count(e) from Employee as e where e.firstName = :fn and e.lastName =:ln", Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();
//        Long singleResult = entityManager.createQuery("select count(e) from Employee as e " + "where e.firstName = :fn " +
//                "and e.lastName = :ln", Long.class)
//                .setParameter("fn", firstName)
//                .setParameter("ln", lastName)
//                .getSingleResult();
        if (singleResult > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    private void solveProblemTwo() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town as t set t.name = upper(t.name) where length(t.name) <= :length ")
                .setParameter("length", 5);
        int affectedRows = query.executeUpdate();
        entityManager.getTransaction().commit();
        System.out.println(affectedRows);

//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("update Town as t " + "set t.name = upper( t.name ) " +
//                "where length(t.name) <= 5");
//        int affectedRows = query.executeUpdate();
//        entityManager.getTransaction().commit();
//        System.out.println(affectedRows);
    }
}
