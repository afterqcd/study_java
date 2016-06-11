package com.afterqcd.study.java.jdbc.connpool;

import com.afterqcd.study.java.jdbc.Student;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by afterqcd on 16/6/11.
 */
public class EntryPoint {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();
    static {
        try {
            // 注册JDBC驱动
            DATA_SOURCE.setDriverClass(JDBC_DRIVER);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        DATA_SOURCE.setJdbcUrl(DB_URL);
        DATA_SOURCE.setUser(USER);
        DATA_SOURCE.setPassword(PASSWORD);
        DATA_SOURCE.setInitialPoolSize(2);
        DATA_SOURCE.setAcquireIncrement(2);
        DATA_SOURCE.setMaxPoolSize(10);
    }

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement;

        try {
            // 连接数据库
            connection = DATA_SOURCE.getConnection();

            // 创建Statement
            statement = connection.prepareStatement("SELECT id, age, name FROM student");

            // 执行查询
            ResultSet resultSet = statement.executeQuery();

            // 解析ResultSet
            List<Student> students = new ArrayList<Student>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setAge(resultSet.getInt("age"));
                student.setName(resultSet.getString("name"));
                students.add(student);
            }

            System.out.println(students);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
