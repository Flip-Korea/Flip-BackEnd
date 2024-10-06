CREATE TABLE IF NOT EXISTS account
(
    account_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    oauth_id      VARCHAR(255)    NOT NULL UNIQUE,
    account_state VARCHAR(25)     NOT NULL,
    recent_login  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (account_id)
);

CREATE TABLE IF NOT EXISTS block
(
    block_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    blocked_id BIGINT UNSIGNED NOT NULL,
    blocker_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (block_id),
    FOREIGN KEY (blocked_id) REFERENCES account (account_id),
    FOREIGN KEY (blocker_id) REFERENCES account (account_id)
);

CREATE TABLE IF NOT EXISTS profile
(
    profile_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id    VARCHAR(20)     NOT NULL UNIQUE,
    nickname   VARCHAR(40)     NOT NULL,
    introduce  TEXT            NOT NULL,
    account_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (profile_id),
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

CREATE TABLE IF NOT EXISTS profile_image
(
    profile_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    image_url  VARCHAR(255)    NOT NULL,

    PRIMARY KEY (profile_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id)
);

CREATE TABLE IF NOT EXISTS follow
(
    follow_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    following_id BIGINT UNSIGNED NOT NULL,
    follower_id  BIGINT UNSIGNED NOT NULL,
    fallow_at    DATETIME        NOT NULL,

    PRIMARY KEY (follow_id),
    FOREIGN KEY (following_id) REFERENCES profile (profile_id),
    FOREIGN KEY (follower_id) REFERENCES profile (profile_id)
);


CREATE TABLE IF NOT EXISTS category
(
    category_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(25)     NOT NULL UNIQUE,

    PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS interest_category
(
    interest_category_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    profile_id           BIGINT UNSIGNED NOT NULL,
    category_id          BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (interest_category_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE IF NOT EXISTS post
(
    post_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    title       VARCHAR(100)    NOT NULL,
    content     TEXT            NOT NULL,
    post_at     DATETIME        NOT NULL,
    bg_color    VARCHAR(25)     NOT NULL,
    font_style  VARCHAR(25)     NOT NULL,
    like_cnt    BIGINT UNSIGNED NOT NULL,
    profile_id  BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (post_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE IF NOT EXISTS comment
(
    comment_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    content    TEXT            NOT NULL,
    comment_at DATETIME        NOT NULL,
    profile_id BIGINT UNSIGNED NOT NULL,
    post_id    BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (comment_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

CREATE TABLE IF NOT EXISTS blame
(
    blame_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    type        VARCHAR(25)     NOT NULL,
    blame_at    DATETIME        NOT NULL,
    post_id     BIGINT UNSIGNED NULL,
    comment_id  BIGINT UNSIGNED NULL,
    reporter_id BIGINT UNSIGNED NOT NULL,
    reported_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (blame_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id),
    FOREIGN KEY (comment_id) REFERENCES comment (comment_id),
    FOREIGN KEY (reporter_id) REFERENCES account (account_id),
    FOREIGN KEY (reported_id) REFERENCES account (account_id)
);

CREATE TABLE IF NOT EXISTS scrap
(
    scrap_id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    profile_id    BIGINT UNSIGNED NOT NULL,
    post_id       BIGINT UNSIGNED NOT NULL,
    scrap_comment TEXT            NOT NULL,

    PRIMARY KEY (scrap_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

CREATE TABLE IF NOT EXISTS temp_post
(
    post_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    title       VARCHAR(100)    NOT NULL,
    content     TEXT            NOT NULL,
    post_at     DATETIME        NOT NULL,
    bg_color    VARCHAR(25)     NOT NULL,
    font_style  VARCHAR(25)     NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    profile_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (post_id),
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE IF NOT EXISTS tag
(
    tag_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    tag_name VARCHAR(255)    NOT NULL UNIQUE,

    PRIMARY KEY (tag_id)
);

CREATE TABLE IF NOT EXISTS post_tag
(
    post_tag_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    post_id     BIGINT UNSIGNED NOT NULL,
    tag_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (post_tag_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);

CREATE TABLE IF NOT EXISTS notification
(
    noti_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    type        VARCHAR(25)     NOT NULL,
    noti_at     DATETIME        NOT NULL,
    is_read     TINYINT(1)      NOT NULL DEFAULT 0,
    sender_id   BIGINT UNSIGNED NOT NULL,
    receiver_id BIGINT UNSIGNED NOT NULL,
    comment_id  BIGINT UNSIGNED NULL,
    post_id     BIGINT UNSIGNED NULL,

    PRIMARY KEY (noti_id),
    FOREIGN KEY (sender_id) REFERENCES profile (profile_id),
    FOREIGN KEY (receiver_id) REFERENCES profile (profile_id),
    FOREIGN KEY (comment_id) REFERENCES comment (comment_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

