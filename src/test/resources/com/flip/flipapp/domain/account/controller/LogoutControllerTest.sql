INSERT INTO account ( account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (1, 'kakaooauth1', 'ACTIVE', 1, null);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (1, 'user1', 'nickname1', '자기소개1', 1);

INSERT INTO token (token_id, refresh_token, profile_id)
VALUES (1, 'refresh_token1', 1);