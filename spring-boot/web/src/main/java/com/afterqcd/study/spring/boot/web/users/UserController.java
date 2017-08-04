package com.afterqcd.study.spring.boot.web.users;

import com.afterqcd.study.spring.boot.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ConcurrentHashMap<Long, User> allUsers;

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable long userId) {
        return Optional
                .ofNullable(allUsers.get(userId))
                .orElseThrow(ResourceNotFoundException::new);
    }
}
