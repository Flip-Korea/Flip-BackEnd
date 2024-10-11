ALTER TABLE block
    ADD UNIQUE KEY block_unique (blocked_id, blocker_id);
