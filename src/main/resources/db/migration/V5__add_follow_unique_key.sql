ALTER TABLE follow
    ADD UNIQUE KEY follow_unique (following_id, follower_id);