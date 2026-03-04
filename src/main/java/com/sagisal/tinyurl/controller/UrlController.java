package com.sagisal.tinyurl.controller;

import com.sagisal.tinyurl.dto.*;
import com.sagisal.tinyurl.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shorten(
            @Valid @RequestBody ShortenRequest request) {

        ShortenResponse response = urlService.shorten(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        urlService.trackClick(shortCode);   // async — doesn't slow down redirect
        return ResponseEntity
            .status(HttpStatus.FOUND)      // 302 — we want analytics, not browser cache
            .location(URI.create(originalUrl))
            .build();
    }
}

