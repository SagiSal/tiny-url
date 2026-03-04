package com.sagisal.tinyurl.dto;

import jakarta.validation.constraints.*;

public record ShortenRequest(

    @NotBlank(message = "URL must not be blank")
    @Size(max = 2048, message = "URL too long")
    @Pattern(
        regexp = "^https?://.*",
        message = "URL must start with http:// or https://"
    )
    String url

) {}
