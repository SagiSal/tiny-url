package com.sagisal.tinyurl.util;

import org.springframework.stereotype.Component;


@Component
public class Base62Encoder {

    private static final String ALPHABET =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public String encode(long id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive");
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(ALPHABET.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return sb.reverse().toString();
    }

    // encode(1)      → "1"
    // encode(100000) → "q0U"   ← start IDs here to avoid single-char codes
    // encode(999999) → "4c91"
}
