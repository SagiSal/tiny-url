package com.sagisal.tinyurl.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String shortCode) {
        super("URL not found: " + shortCode);
    }
}
