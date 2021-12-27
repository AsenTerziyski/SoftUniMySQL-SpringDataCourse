import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class DatabaseAccessWithJDBC {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the salary you want to search about:");
        double slr = Double.parseDouble(scanner.nextLine());

        System.out.println("***********************************************************************");
        System.out.println();
//        System.out.println(Driver.class);
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni",
                "root", "M@lmsuite3996");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select distinct * from employees where employees.salary > " + slr);
        
        // resultSet returns boolean:
//        if(resultSet.next()) {
//            String jobTitle = resultSet.getString(5);
//            System.out.println(jobTitle);
//        }
        
        while (resultSet.next()) {
            String jobTitle = resultSet.getString(5);
            double salary = resultSet.getDouble(9);
            long id = resultSet.getLong(1);
            System.out.println("id" + id + " " + jobTitle + " with salary: " + salary);
        }
        System.out.println();
        System.out.println("***********************************************************************");

    }
}
