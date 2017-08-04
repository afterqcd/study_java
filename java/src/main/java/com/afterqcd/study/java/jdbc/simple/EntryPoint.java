package com.afterqcd.study.java.jdbc.simple;

import com.afterqcd.study.java.jdbc.Student;

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

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 注册JDBC驱动
            Class.forName(JDBC_DRIVER);

            // 连接数据库
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
