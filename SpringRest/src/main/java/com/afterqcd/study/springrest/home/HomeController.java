package com.afterqcd.study.springrest.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by afterqcd on 16/6/1.
 */
@RestController
public class HomeController {

    @RequestMapping({"/", "/home"})
    public String home() {
        return "Hello Spring Web!";
    }
}
