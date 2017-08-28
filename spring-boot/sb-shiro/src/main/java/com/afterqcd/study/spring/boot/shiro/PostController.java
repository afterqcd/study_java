package com.afterqcd.study.spring.boot.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private Map<Long, Post> allPosts;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("post:list")
    List<Post> getPosts() {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return new ArrayList<>(allPosts.values());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions("post:read")
    Post getPost(@PathVariable long id) {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return allPosts.get(id);
    }
}
