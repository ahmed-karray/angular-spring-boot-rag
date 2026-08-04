ALTER TABLE documents ADD COLUMN file_path VARCHAR(1000);
-- Backfill first if you have existing rows with data — see note below — then:
ALTER TABLE documents ALTER COLUMN file_path SET NOT NULL;
ALTER TABLE documents DROP COLUMN data;