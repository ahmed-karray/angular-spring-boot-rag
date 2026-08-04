export interface DocumentItem {
  id: number;
  filename: string;
  contentType: string;
  size: number;
  uploadedAt: string;
  uploadedByUsername: string;
  uploadedByDepartment: string | null;
  tags: string[];
  rootDocumentId: number;
  versionNumber: number;
  isLatest: boolean;
  visibility: 'PRIVATE' | 'DEPARTMENT' | 'SPECIFIC_USERS' | 'PUBLIC';
  sharedWithUsernames: string[];
  extractionStatus: 'PENDING' | 'SUCCESS' | 'FAILED' | 'NEEDS_OCR' | 'EMPTY';
  extractionMethod: 'tika' | 'pdftotext' | 'pdfbox' | 'ocr' | 'mixed' | 'needs_ocr' | 'none' | null;
}
