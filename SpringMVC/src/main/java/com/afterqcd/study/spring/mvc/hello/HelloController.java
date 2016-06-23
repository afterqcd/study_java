package com.afterqcd.study.spring.mvc.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by afterqcd on 16/6/22.
 */
@Controller
public class HelloController {
    @Value("${home.message}")
    private String message;

    @RequestMapping("/hello")
    @ResponseBody
    public String home() {
        return message;
    }
}
