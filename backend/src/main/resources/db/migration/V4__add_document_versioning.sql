ALTER TABLE documents ADD COLUMN root_document_id BIGINT;
ALTER TABLE documents ADD COLUMN version_number INTEGER NOT NULL DEFAULT 1;
ALTER TABLE documents ADD COLUMN is_latest BOOLEAN NOT NULL DEFAULT true;

-- Existing documents each become their own root, version 1
UPDATE documents SET root_document_id = id WHERE root_document_id IS NULL;