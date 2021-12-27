package ormFramework.core;

import ormFramework.annotation.Column;
import ormFramework.annotation.Entity;
import ormFramework.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class EntityManagerImp implements EntityManager {

    private final Connection connection;

    public EntityManagerImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T findById(int id, Class<T> type) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String tableName = type.getAnnotation(Entity.class).tableName();

        Field field = Arrays
                .stream(type.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow();

        String idColumnName = field.getName();
        PreparedStatement preparedStatement
                = this.connection.prepareStatement
                                ("SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?");
        preparedStatement.setInt(1, id);
        T entity = (T)type.getConstructors()[0].newInstance();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        for (Field field1 : type.getDeclaredFields()) {
            if (field1.isAnnotationPresent(Column.class)) {
                Column columnInfo = field1.getAnnotation(Column.class);
                String setterName = "set" + ((field1.getName().charAt(0) + "").toUpperCase()) +
                        field1.getName().substring(1);
                if (field1.getType().equals(String.class)) {
                    String s = resultSet.getString(columnInfo.name());
                    type.getMethod(setterName, String.class).invoke(entity, s);
                } else {
                    int i = resultSet.getInt(columnInfo.name());
                    type.getMethod(setterName, field1.getType()).invoke(entity, i);
                }
            } else if (field1.isAnnotationPresent(Id.class)) {
                String setterName = "set" + ((field1.getName().charAt(0) + "").toUpperCase()) +
                        field1.getName().substring(1);
                type.getMethod(setterName, int.class).invoke(entity,id);

            }
        }
        return entity;


    }
}
