CREATE TABLE IF NOT EXISTS urls (
    id           BIGSERIAL PRIMARY KEY,
    short_code   VARCHAR(10)  UNIQUE NOT NULL,
    original_url TEXT         NOT NULL,
    click_count  BIGINT       DEFAULT 0,
    expires_at   TIMESTAMPTZ,
    created_at   TIMESTAMPTZ  DEFAULT NOW(),
    is_active    BOOLEAN      DEFAULT TRUE
);

-- Index for fast redirect lookup
CREATE INDEX IF NOT EXISTS idx_urls_short_code ON urls(short_code);