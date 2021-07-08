package com.example.javakotlinmix;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qiuchangdong
 * @Type Controller
 * @Desc 。
 * @date 2021-07-08 09:40:10
 */
@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello() + " -> Java Controller";
    }

    @GetMapping("/data")
    public List<String> data() {
        return helloService.data();
    }
}
