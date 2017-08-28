package com.afterqcd.study.spring.boot.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Posts {
    @Bean
    public Map<Long, Post> allPosts() {
        HashMap<Long, Post> posts = new HashMap<>();
        posts.put(1L, new Post(1L, "someting happened", new Date()));
        posts.put(2L, new Post(2L, "another thing happened", new Date()));
        return posts;
    }
}
