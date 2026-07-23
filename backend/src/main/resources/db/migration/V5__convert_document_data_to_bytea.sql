ALTER TABLE documents
ALTER COLUMN data TYPE bytea
    USING lo_get(data::oid);

ALTER TABLE documents ALTER COLUMN data SET NOT NULL;