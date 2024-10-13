ALTER TABLE block
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (block_id),
    ADD UNIQUE KEY block_unique (blocked_id, blocker_id);
