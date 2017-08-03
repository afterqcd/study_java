package com.afterqcd.study.spring.boot.web.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resource) {
        super(resource + " not found.");
    }

    public ResourceNotFoundException(String resource, Throwable cause) {
        super(resource + " not found.", cause);
    }
}
