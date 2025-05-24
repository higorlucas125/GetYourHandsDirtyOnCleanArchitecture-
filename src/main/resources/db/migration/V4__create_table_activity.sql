CREATE TABLE IF NOT EXISTS activity (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          local_date_time TIMESTAMP NOT NULL,
                          owner_account_id BIGINT NOT NULL,
                          source_account_id BIGINT NOT NULL,
                          target_account_id BIGINT NOT NULL,
                          amount BIGINT NOT NULL
);