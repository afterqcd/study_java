package com.afterqcd.study.spring.boot.sql.jpa;

import com.afterqcd.study.spring.boot.sql.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);

    @Query("select u from users u where u.id = :id")
    User queryById(@Param("id") long id);
}
