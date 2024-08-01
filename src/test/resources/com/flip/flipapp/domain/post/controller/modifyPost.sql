INSERT INTO category
VALUES (1, 'DAILY');
INSERT INTO category
VALUES (2, 'IT_SCIENCE');
INSERT INTO category
VALUES (3, 'TIPS');
INSERT INTO category
VALUES (4, 'THOUGHTS');

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (1, 'oauth', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (1, 'user', 'nickname', '자기소개', 1);

INSERT INTO tag (tag_id, tag_name)
VALUES (1, 'tag1');

INSERT INTO tag (tag_id, tag_name)
VALUES (2, 'tag2');

INSERT INTO tag (tag_id, tag_name)
VALUES (3, 'tag3');

INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (1, 'title', 'content', now(), 'RED', 'BOLD', 0, 1, 1);

INSERT INTO post_tag (post_tag_id, post_id, tag_id)
VALUES (1, 1, 1);

INSERT INTO post_tag (post_tag_id, post_id, tag_id)
VALUES (2, 1, 2);