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

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (2, 'oauth2', 'ACTIVE', 2);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (2, 'user2', 'nickname', '자기소개', 1);

INSERT INTO temp_post (post_id, title, content, post_at, bg_color, font_style, category_id,
                       profile_id)
VALUES (1, 'title', 'content', now(), 'RED', 'BOLD', 1, 1);

INSERT INTO temp_post (post_id, title, content, post_at, bg_color, font_style, category_id,
                       profile_id, tags)
VALUES (2, 'title2', 'content2', now(), 'RED', 'BOLD', 1, 2, 'tag1');

INSERT INTO temp_post (post_id, title, content, post_at, bg_color, font_style, category_id,
                       profile_id, tags)
VALUES (3, 'title3', 'content3', now(), 'RED', 'BOLD', 3, 1, 'tag1');

INSERT INTO temp_post (post_id, title, content, post_at, bg_color, font_style, category_id,
                       profile_id, tags)
VALUES (4, 'title4', 'content4', now(), 'RED', 'BOLD', 2, 1, 'tag1');

INSERT INTO temp_post (post_id, title, content, post_at, bg_color, font_style, category_id,
                       profile_id, tags)
VALUES (5, 'title5', 'content5', now(), 'RED', 'BOLD', 1, 1, 'tag1');