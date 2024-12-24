INSERT INTO account (account_id, oauth_id, account_state, recent_login, suspended_at, ads_agree)
VALUES (1, 'kakaooauth456', 'ACTIVE', 1, null, true);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id, following_cnt, follower_cnt, post_cnt)
VALUES (1, 'user456', 'nickname456', '자기소개1', 1, 0, 0, 0);