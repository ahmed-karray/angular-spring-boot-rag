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
}