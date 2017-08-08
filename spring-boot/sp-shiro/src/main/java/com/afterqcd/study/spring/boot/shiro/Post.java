package com.afterqcd.study.spring.boot.shiro;

import java.util.Date;

public class Post {
    private long id;
    private String title;
    private Date createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Post() {
    }

    public Post(long id, String title, Date createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }
}
