INSERT INTO account (account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (1, 'kakaooauth1', 'ACTIVE', 1, null);
INSERT INTO account (account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (2, 'kakaooauth2', 'ACTIVE', 2, null);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id, follower_cnt, following_cnt, post_cnt)
VALUES (1, 'user1', 'nickname1', '자기소개1', 1, 0, 0, 0);
INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id, follower_cnt, following_cnt, post_cnt)
VALUES (2, 'user2', 'nickname2', '자기소개2', 2, 0, 0, 0);

INSERT INTO profile_image (profile_id, image_url)
VALUES (1, 'https://example.com/image1');