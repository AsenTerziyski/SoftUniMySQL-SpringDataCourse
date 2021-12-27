package ormFramework.core;

import ormFramework.annotation.Column;
import ormFramework.annotation.Entity;
import ormFramework.annotation.Id;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerFactory {
    public static EntityManager create
            (String dbType,
             String host,
             int port,
             String user,
             String pass,
             String dbName,
             Class<?> mainClass) throws SQLException, URISyntaxException, ClassNotFoundException {

        Connection connection = createConnection(dbType, host, port, user, pass, dbName);
        List<Class<?>> classes = getEntities(mainClass);
//        createTables(connection, classes);
        return new EntityManagerImp(connection);

    }

    private static void createTables(Connection connection, List<Class<?>> classes) throws SQLException {
        for (Class aClass : classes) {
            Entity entityInfo = (Entity) aClass.getAnnotation(Entity.class);
            String sqlStatement = "CREATE TABLE ";
            String tableName = entityInfo.tableName();
            sqlStatement = sqlStatement + tableName + "(\n";
            String primaryKeyDefinition = "";

            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    sqlStatement = sqlStatement + field.getName() + " int auto_increment, \n";
                    primaryKeyDefinition = "\nconstraint " + tableName + "_pk \nprimary key (" + field.getName() + ")";

                } else if (field.isAnnotationPresent(Column.class)) {
                    Column columnInfo = field.getAnnotation(Column.class);
                    sqlStatement = sqlStatement + columnInfo.name() + " " + columnInfo.columnDefinition() + ",";

                }

            }
            sqlStatement = sqlStatement + " " + primaryKeyDefinition + "\n);";
            System.out.println(sqlStatement);

            connection.createStatement().execute(sqlStatement);
        }
    }

    private static List<Class<?>> getEntities(Class<?> mainClass) throws URISyntaxException, ClassNotFoundException {
        String path = mainClass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        System.out.println(path);
        String packageName = mainClass.getPackageName();
        File rootDir = new File(path + packageName.replace(".", "/"));
        List<Class<?>> classes = new ArrayList<>();
        scanEntities(rootDir, packageName, classes);
        return classes;
    }

    private static Connection createConnection(String dbType, String host, int port, String user, String pass, String dbName) throws SQLException {
        Connection connection = DriverManager
                .getConnection("jdbc:" + dbType + "://" + host + ":" + port + "/" + dbName,
                        user,
                        pass);
        return connection;
    }

    private static void scanEntities(File dir, String packageName, List<Class<?>> classes) throws ClassNotFoundException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanEntities(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                Class<?> classInfo = Class.forName(packageName + "." + file.getName().replace(".class", ""));
                if (classInfo.isAnnotationPresent(Entity.class)) {
                    classes.add(classInfo);
                }
            }
        }
    }
}
