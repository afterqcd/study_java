package com.afterqcd.study.spring.boot.sql.jdbc;

import com.afterqcd.study.spring.boot.sql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    User findUser(long userId) {
        return jdbcTemplate.queryForObject(
                "select id, name, age from users where id = ?",
                new BeanPropertyRowMapper<>(User.class),
                userId
        );
    }

    void createUser(User user) {
        jdbcTemplate.update(
                "insert into users(id, name, age) values (?, ?, ?)",
                user.getId(), user.getName(), user.getAge()
        );
    }
}
