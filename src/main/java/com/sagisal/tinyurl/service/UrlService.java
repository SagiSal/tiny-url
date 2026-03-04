package com.sagisal.tinyurl.service;

import com.sagisal.tinyurl.dto.*;
import com.sagisal.tinyurl.exception.UrlNotFoundException;
import com.sagisal.tinyurl.model.Url;
import com.sagisal.tinyurl.repository.UrlRepository;
import com.sagisal.tinyurl.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder  base62Encoder;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public ShortenResponse shorten(ShortenRequest request) {
        // 1. Save with empty short_code to get the auto-generated ID
        Url url = Url.builder()
            .originalUrl(request.url())
            .shortCode("temp")
            .build();
        url = urlRepository.save(url);

        // 2. Encode the ID to Base62
        String code = base62Encoder.encode(url.getId());
        url.setShortCode(code);
        urlRepository.save(url);

        return new ShortenResponse(
            baseUrl + "/" + code,
            request.url(),
            code
        );
    }

    @Cacheable(value = "urls", key = "#shortCode")
    public String getOriginalUrl(String shortCode) {
        return urlRepository
            .findByShortCodeAndIsActiveTrue(shortCode)
            .map(Url::getOriginalUrl)
            .orElseThrow(() -> new UrlNotFoundException(shortCode));
    }

    @Async
    @Transactional
    public void trackClick(String shortCode) {
        urlRepository.incrementClickCount(shortCode);
    }
}
