package com.afterqcd.study.spring.boot.graphql.blog.resolver;

import com.afterqcd.study.spring.boot.graphql.blog.Data;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Author;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Post;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by afterqcd on 2017/8/26.
 */
@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private Data data;

    public List<Post> posts() {
        return data.getPosts();
    }

    public Post post(String id) {
        return data.getPost(id);
    }

    public List<Author> authors() {
        return data.getAuthors();
    }

    public Author author(String id) {
        return data.getAuthor(id);
    }
}
