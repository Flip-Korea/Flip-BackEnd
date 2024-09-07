INSERT INTO account ( account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (1, 'kakaooauth1', 'ACTIVE', 1, null);

INSERT INTO account ( account_id, oauth_id, account_state, recent_login, suspended_at)
VALUES (2, 'kakaooauth2', 'SUSPENDED_5_DAYS', 1, '2024-08-30 18:30:15');

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (1, 'user1', 'nickname1', '자기소개1', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (2, 'user2', 'nickname2', '자기소개2', 2);

INSERT INTO blame (blame_id, type, blame_at, reporter_id, reported_id)
VALUES (1, 'DISLIKE', '2024-07-01 00:00:00', 1, 2);

INSERT INTO blame (blame_id, type, blame_at, reporter_id, reported_id)
VALUES (2, 'DISLIKE', '2024-07-01 00:00:00', 1, 2);

INSERT INTO blame (blame_id, type, blame_at, reporter_id, reported_id)
VALUES (3, 'SPAM_OR_ADVERTISEMENT', '2024-07-01 00:00:00', 1, 2);

INSERT INTO blame (blame_id, type, blame_at, reporter_id, reported_id)
VALUES (4, 'INAPPROPRIATE', '2024-07-01 00:00:00', 1, 2);
