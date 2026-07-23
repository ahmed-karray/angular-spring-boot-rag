ALTER TABLE documents ADD COLUMN visibility VARCHAR(50) NOT NULL DEFAULT 'PUBLIC';

CREATE TABLE document_shares (
                                 document_id BIGINT NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
                                 user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                 PRIMARY KEY (document_id, user_id)
);