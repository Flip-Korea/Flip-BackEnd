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
VALUES (2, 'oauth2', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (2, 'user2', 'nickname2', '자기소개', 1);

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (3, 'oauth3', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (3, 'user3', 'nickname3', '자기소개', 1);

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (4, 'oauth4', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (4, 'user4', 'nickname4', '자기소개', 1);

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (5, 'oauth5', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (5, 'user5', 'nickname5', '자기소개', 1);

INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (1, 'title', 'content', now(), 'RED', 'BOLD', 0, 2, 1);
INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (2, 'title2', 'content2', now(), 'RED', 'BOLD', 0, 2, 1);
INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (3, 'title3', 'content3', now(), 'RED', 'BOLD', 0, 3, 1);
INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (4, 'title4', 'content4', now(), 'RED', 'BOLD', 0, 4, 1);
INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (5, 'title5', 'content4', now(), 'RED', 'BOLD', 0, 5, 1);

INSERT INTO scrap (scrap_id, profile_id, post_id, scrap_comment)
VALUES (1, 1, 2, 'comment1');

INSERT INTO scrap (scrap_id, profile_id, post_id, scrap_comment)
VALUES (2, 2, 1, 'comment2');

INSERT INTO scrap (scrap_id, profile_id, post_id, scrap_comment)
VALUES (3, 1, 3, 'comment3');

INSERT INTO scrap (scrap_id, profile_id, post_id, scrap_comment)
VALUES (4, 1, 4, 'comment4');

INSERT INTO scrap (scrap_id, profile_id, post_id, scrap_comment)
VALUES (5, 1, 5, 'comment5');
