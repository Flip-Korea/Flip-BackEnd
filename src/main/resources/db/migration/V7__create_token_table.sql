CREATE TABLE IF NOT EXISTS token
(
    token_id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    refresh_token VARCHAR(500)    NOT NULL UNIQUE,
    profile_id    BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (token_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id)
);