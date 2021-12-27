import entity.pr02SalesDB.Customer;
import entity.pr02SalesDB.Product;
import entity.pr02SalesDB.Sale;
import entity.pr02SalesDB.StoreLocation;
import entity.pr03UniversitySystem.Course;
import entity.pr03UniversitySystem.Student;
import entity.pr03UniversitySystem.Teacher;
import entity.pr04HospitalDatabase.Diagnose;
import entity.pr04HospitalDatabase.Medicament;
import entity.pr04HospitalDatabase.Patient;
import entity.pr04HospitalDatabase.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        System.out.println();
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX-BEGIN-XXXXXXXXXXXXXXXXXXXXXXXX");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // TEST PROBLEM 02:
//        Sale testSale = new Sale();
//        testSale.setLocalDate(LocalDateTime.now());
//
//        Product testProduct = new Product();
//        testProduct.setName("kifla");
//        testProduct.setPrice(BigDecimal.valueOf(2.5));
//        testProduct.setQuantity(10);
//
//        testSale.setProduct(testProduct);
//
//        Customer testCustomer = new Customer();
//        testCustomer.setName("TestCustomer");
//        testCustomer.setEmail("test@test.bg");
//        testCustomer.setCreditCardNumber("12345678");
//
//        testSale.setCustomer(testCustomer);
//
//        StoreLocation storeLocation = new StoreLocation();
//        storeLocation.setLocationName("TestLocation");
//
//        testSale.setStoreLocation(storeLocation);
//
//        Set<Sale> sales = new HashSet<>();
//        sales.add(testSale);
//
//        testProduct.setSales(sales);
//        testCustomer.setSales(sales);
//        storeLocation.setSales(sales);
//
//        entityManager.persist(testSale);
//        entityManager.persist(testCustomer);
//        entityManager.persist(testProduct);
//        entityManager.persist(storeLocation);
//
//        Customer testCustomer2 = new Customer();
//        testCustomer2.setName("TestCustomer2");
//        testCustomer2.setEmail("test2@test.com");
//        entityManager.persist(testCustomer2);
//
//        Product foundProduct = entityManager.find(Product.class, 1L);
//        System.out.printf("Founded product name is %s with price %.2f.", foundProduct.getName(), foundProduct.getPrice());
//        entityManager.remove(foundProduct);

//        // TEST PROBLEM 03:
//        Student student = new Student();
//        student.setAttendance(2);
//        student.setFirstName("Test1");
//        student.setLastName("Test1");
//        student.setAvgGrade((float)5.57);
//        entityManager.persist(student);
//
//        Teacher teacher = new Teacher();
//        teacher.setFirstName("TestTeacher1");
//        teacher.setLastName("TestTeacher1");
//        teacher.setSalary(BigDecimal.valueOf(500.000));
//        entityManager.persist(teacher);
//
//        Course course = new Course();
//        course.setName("testCourse1");
//        Set<Student> students = new HashSet<>();
//        students.add(student);
//        course.setStudents(students);
//        course.setTeacher(teacher);
//        entityManager.persist(course);

        entityManager.getTransaction().commit();

        System.out.println();
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX-END-XXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
