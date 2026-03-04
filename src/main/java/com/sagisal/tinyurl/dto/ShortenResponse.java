package com.sagisal.tinyurl.dto;

public record ShortenResponse(
    String shortUrl,
    String originalUrl,
    String shortCode
) {}
