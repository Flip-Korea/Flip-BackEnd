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
VALUES (2, 'user2', 'nickname2', '자기소개', 2);

INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (1, 'title', 'content', now(), 'RED', 'BOLD', 0, 1, 1);

INSERT INTO post (post_id, title, content, post_at, bg_color, font_style, like_cnt, profile_id,
                  category_id)
VALUES (2, 'title2', 'content2', now(), 'RED', 'BOLD', 0, 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (1, 'hi', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (2, 'hi2', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (3, 'hi3', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (4, 'hi4', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (5, 'hi5', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (6, 'hi6', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (7, 'hi7', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (8, 'hi8', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (9, 'hi9', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (10, 'hi10', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (11, 'hi11', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (12, 'hi12', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (13, 'hi13', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (14, 'hi14', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (15, 'hi15', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (16, 'hi16', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (17, 'hi17', now(), 1, 1);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (18, 'hi18', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (19, 'hi19', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (20, 'hi20', now(), 1, 2);

INSERT INTO comment(comment_id, content, comment_at, profile_id, post_id)
VALUES (21, 'hi21', now(), 2, 2);