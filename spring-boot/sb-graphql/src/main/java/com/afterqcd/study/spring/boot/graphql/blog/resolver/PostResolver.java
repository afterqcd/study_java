package com.afterqcd.study.spring.boot.graphql.blog.resolver;

import com.afterqcd.study.spring.boot.graphql.blog.Data;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Author;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Comment;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Post;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by afterqcd on 2017/8/26.
 */
@Component
public class PostResolver implements GraphQLResolver<Post> {
    @Autowired
    private Data data;

    public Author author(Post post) {
        return data.getAuthor(post.getAuthorId());
    }

    public List<Comment> comments(Post post) {
        return data.getComments(post.getId());
    }
}
