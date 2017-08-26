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
public class AuthorResovler implements GraphQLResolver<Author> {
    @Autowired
    private Data data;

    public List<Post> posts(Author author) {
        return data.getPostsByAuthorId(author.getId());
    }

    public List<Comment> comments(Author author) {
        return data.getCommentsByAuthorId(author.getId());
    }
}
