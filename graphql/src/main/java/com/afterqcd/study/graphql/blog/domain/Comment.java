package com.afterqcd.study.graphql.blog.domain;

public class Comment {
    private String id;
    private String content;
    private String postId;
    private String authorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Comment(String id, String content, String postId, String authorId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.authorId = authorId;
    }
}
