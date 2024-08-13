INSERT INTO account (account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (1, 'oauth', 'ACTIVE', 1, null);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (1, 'user', 'nickname', '자기소개', 1);
