ALTER TABLE documents ADD COLUMN extracted_text TEXT;
ALTER TABLE documents ADD COLUMN extraction_status VARCHAR(50) NOT NULL DEFAULT 'PENDING';