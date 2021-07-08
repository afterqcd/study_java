package com.example.javakotlinmix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuchangdong
 * @Type Controller
 * @Desc 。
 * @date 2021-07-08 09:40:10
 */
@RestController
@RequiredArgsConstructor
public class Controller {
    private final Service service;

    @GetMapping("/hello")
    public String hello() {
        return "Java Controller -> " + service.hello();
    }
}
