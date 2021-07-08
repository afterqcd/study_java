package com.example.javakotlinmix;

import java.util.Arrays;
import java.util.List;

/**
 * @author qiuchangdong
 * @Type Repository
 * @Desc 。
 * @date 2021-07-08 10:04:56
 */
@org.springframework.stereotype.Repository
public class HelloRepository {
    public String hello() {
        return "Java Repository";
    }

    public List<String> data() {
        return Arrays.asList("e1", "e2", "e3");
    }
}
