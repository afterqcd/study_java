package com.afterqcd.study.graphql.blog;

import com.afterqcd.study.graphql.blog.domain.Author;
import com.afterqcd.study.graphql.blog.domain.Comment;
import com.afterqcd.study.graphql.blog.domain.Post;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {
    private List<Author> authors;
    private List<Post> posts;
    private List<Comment> comments;

    private Map<String, Author> authorById;
    private Map<String, Post> postById;

    public Data() {
        initAuthors();
        initPosts();
        initComments();

        initAuthorById();
        initPostById();
    }

    private void initAuthors() {
        this.authors = Lists.newArrayList(
                new Author("a1", "zhang san"),
                new Author("a2", "wang wu")
        );
    }

    private void initPosts() {
        this.posts = Lists.newArrayList(
                new Post("p1", "first post", "the first post", "a1"),
                new Post("p2", "second post", "the second post", "a1"),
                new Post("p3", "third post", "the third post", "a2"),
                new Post("p4", "fourth post", "the fourth post", "a2")
        );
    }

    private void initComments() {
        this.comments = Lists.newArrayList(
                new Comment("c1", "comment belong to first post", "p1", "a2"),
                new Comment("c2", "comment belong to second post", "p2", "a1")
        );
    }

    private void initAuthorById() {
        this.authorById = Maps.newHashMap();
        this.authors.forEach(author -> authorById.put(author.getId(), author));
    }

    private void initPostById() {
        this.postById = Maps.newHashMap();
        this.posts.forEach(post -> this.postById.put(post.getId(), post));
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public Author getAuthor(String authorId) {
        return this.authorById.get(authorId);
    }

    public List<Comment> getComments(String postId) {
        return this.comments.stream()
                .filter(c -> c.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public Post getPost(String id) {
        return this.postById.get(id);
    }

    public List<Author> getAuthors() {
        return this.authors;
    }

    public List<Post> getPostsByAuthorId(String authorId) {
        return this.posts.stream()
                .filter(p -> p.getAuthorId().equals(authorId))
                .collect(Collectors.toList());
    }

    public List<Comment> getCommentsByAuthorId(String authorId) {
        return this.comments.stream()
                .filter(c -> c.getAuthorId().equals(authorId))
                .collect(Collectors.toList());
    }
}
