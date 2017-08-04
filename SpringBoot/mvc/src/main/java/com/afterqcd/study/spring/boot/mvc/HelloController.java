package com.afterqcd.study.spring.boot.mvc;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by afterqcd on 2017/5/23.
 */
@Controller
@ResponseBody
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring MVC";
    }

    @RequestMapping("/users")
    public Page<User> users() {
        List<User> users = Lists.newArrayList(
                new User("zhang san", 15),
                new User("wang wu", 20)
        );
        Pageable pageable = new PageRequest(0, 10);
        return new PageImpl<>(users, pageable, users.size());
    }
}
