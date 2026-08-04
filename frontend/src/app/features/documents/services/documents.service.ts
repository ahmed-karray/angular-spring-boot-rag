import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, interval } from 'rxjs';
import { switchMap, takeWhile, startWith, first } from 'rxjs/operators';
import { DocumentItem } from '../../../shared/models';

@Injectable({ providedIn: 'root' })
export class Documents {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8081/api/documents';

  upload(file: File, tags: string[]): Observable<DocumentItem> {
    const formData = new FormData();
    formData.append('file', file);
    tags.forEach((tag) => formData.append('tags', tag));
    return this.http.post<DocumentItem>(this.baseUrl, formData);
  }

  search(
    name?: string,
    fromDate?: string,
    toDate?: string,
    department?: string,
    uploadedBy?: string,
    tag?: string,
  ): Observable<DocumentItem[]> {
    let params: Record<string, string> = {};
    if (name) params['name'] = name;
    if (fromDate) params['fromDate'] = fromDate;
    if (toDate) params['toDate'] = toDate;
    if (department) params['department'] = department;
    if (uploadedBy) params['uploadedBy'] = uploadedBy;
    if (tag) params['tag'] = tag;

    return this.http.get<DocumentItem[]>(this.baseUrl, { params });
  }

  getAllTags(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/tags`);
  }

  download(id: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${id}/download`, { responseType: 'blob' });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  uploadNewVersion(documentId: number, file: File, tags: string[]): Observable<DocumentItem> {
    const formData = new FormData();
    formData.append('file', file);
    tags.forEach((tag) => formData.append('tags', tag));
    return this.http.post<DocumentItem>(`${this.baseUrl}/${documentId}/versions`, formData);
  }

  getVersionHistory(documentId: number): Observable<DocumentItem[]> {
    return this.http.get<DocumentItem[]>(`${this.baseUrl}/${documentId}/versions`);
  }

  updateSharing(
    documentId: number,
    visibility: string,
    usernames: string[],
  ): Observable<DocumentItem> {
    return this.http.put<DocumentItem>(`${this.baseUrl}/${documentId}/sharing`, {
      visibility,
      usernames,
    });
  }

  /**
   * Fetches a single document's current state. Needed now that extraction runs
   * async on the backend — the object returned from upload() has
   * extractionStatus: 'PENDING' and no extractedText yet, so callers need a way
   * to check back on it.
   */
  getById(id: number): Observable<DocumentItem> {
    return this.http.get<DocumentItem>(`${this.baseUrl}/${id}`);
  }

  /**
   * Polls a document every `intervalMs` until its extraction finishes (any
   * status other than PENDING), then completes. Use after upload() to know
   * when it's safe to show extractedText / extractionMethod to the user.
   *
   * Usage:
   *   this.documents.upload(file, tags).pipe(
   *     switchMap(doc => this.documents.pollUntilExtracted(doc.id))
   *   ).subscribe(finalDoc => { ... });
   */
  pollUntilExtracted(id: number, intervalMs = 2000): Observable<DocumentItem> {
    return interval(intervalMs).pipe(
      startWith(0),
      switchMap(() => this.getById(id)),
      first((doc) => doc.extractionStatus !== 'PENDING'),
    );
  }
}
