ALTER TABLE documents DROP COLUMN extracted_pdf_data;
ALTER TABLE documents ADD COLUMN extracted_pdf_path VARCHAR(1000);