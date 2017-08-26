package com.afterqcd.study.spring.boot.graphql.blog.resolver;

import com.afterqcd.study.spring.boot.graphql.blog.Data;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Author;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Comment;
import com.afterqcd.study.spring.boot.graphql.blog.domain.Post;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 2017/8/26.
 */
@Component
public class CommentResovler implements GraphQLResolver<Comment> {
    @Autowired
    private Data data;

    public Author author(Comment comment) {
        return data.getAuthor(comment.getAuthorId());
    }

    public Post post(Comment comment) {
        return data.getPost(comment.getPostId());
    }
}
