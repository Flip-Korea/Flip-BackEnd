INSERT INTO category
VALUES (1, 'DAILY');
INSERT INTO category
VALUES (2, 'IT_SCIENCE');
INSERT INTO category
VALUES (3, 'TIPS');
INSERT INTO category
VALUES (4, 'THOUGHTS');
INSERT INTO category
VALUES (5, 'HI');
INSERT INTO category
VALUES (6, 'HI2');

INSERT INTO account (account_id, oauth_id, account_state, recent_login)
VALUES (1, 'oauth', 'ACTIVE', 1);

INSERT INTO profile (profile_id, user_id, nickname, introduce, account_id)
VALUES (1, 'user', 'nickname', '자기소개', 1);

INSERT INTO interest_category (interest_category_id, profile_id, category_id)
VALUES (1, 1, 1);
INSERT INTO interest_category (interest_category_id, profile_id, category_id)
VALUES (2, 1, 2);
INSERT INTO interest_category (interest_category_id, profile_id, category_id)
VALUES (3, 1, 3);