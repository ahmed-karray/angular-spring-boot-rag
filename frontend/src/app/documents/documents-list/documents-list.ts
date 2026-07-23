import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { Documents } from '../documents';
import { DocumentItem } from '../document.model';

@Component({
  selector: 'app-documents-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, FormsModule],
  templateUrl: './documents-list.html',
  styleUrl: './documents-list.scss',
})
export class DocumentsList implements OnInit {
  private docsService = inject(Documents);
  private fb = inject(FormBuilder);

  documents = signal<DocumentItem[]>([]);
  errorMessage = signal<string | null>(null);
  selectedFile = signal<File | null>(null);
  uploadTagsInput = signal('');

  departments = ['HR', 'FINANCE', 'IT', 'LEGAL', 'MARKETING', 'OPERATIONS', 'OTHER'];

  filterForm: FormGroup = this.fb.group({
    name: [''],
    uploadedBy: [''],
    fromDate: [''],
    toDate: [''],
    department: [''],
    tag: [''],
  });

  // Version history panel state
  viewingHistoryFor = signal<DocumentItem | null>(null);
  versionHistory = signal<DocumentItem[]>([]);

  // New version upload state
  versioningFor = signal<DocumentItem | null>(null);
  newVersionFile = signal<File | null>(null);
  newVersionTagsInput = signal('');

  ngOnInit() {
    this.loadDocuments();

    this.filterForm.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged((a, b) => JSON.stringify(a) === JSON.stringify(b)))
      .subscribe(() => this.loadDocuments());
  }

  loadDocuments() {
    const { name, uploadedBy, fromDate, toDate, department, tag } = this.filterForm.value;

    this.docsService
      .search(name ?? '', fromDate ?? '', toDate ?? '', department ?? '', uploadedBy ?? '', tag ?? '')
      .subscribe({
        next: docs => this.documents.set(docs),
        error: err => this.errorMessage.set(err.error?.error ?? 'Failed to load documents'),
      });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.selectedFile.set(input.files?.[0] ?? null);
  }

  uploadFile() {
    const selectedFile = this.selectedFile();
    if (!selectedFile) return;
    this.errorMessage.set(null);

    const tags = this.uploadTagsInput()
      .split(',')
      .map(t => t.trim())
      .filter(t => t.length > 0);

    this.docsService.upload(selectedFile, tags).subscribe({
      next: () => {
        this.selectedFile.set(null);
        this.uploadTagsInput.set('');
        this.loadDocuments();
      },
      error: err => this.errorMessage.set(err.error?.error ?? 'Upload failed'),
    });
  }

  downloadFile(doc: DocumentItem) {
    this.docsService.download(doc.id).subscribe({
      next: blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = doc.filename;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: err => this.errorMessage.set(err.error?.error ?? 'Download failed'),
    });
  }

  deleteDocument(id: number) {
    if (!confirm('Delete this document?')) return;

    this.docsService.delete(id).subscribe({
      next: () => this.loadDocuments(),
      error: err => this.errorMessage.set(err.error?.error ?? 'Delete failed'),
    });
  }

  formatSize(bytes: number): string {
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }

  // --- Version history ---
  openHistory(doc: DocumentItem) {
    this.viewingHistoryFor.set(doc);
    this.docsService.getVersionHistory(doc.id).subscribe({
      next: versions => this.versionHistory.set(versions),
      error: err => this.errorMessage.set(err.error?.error ?? 'Failed to load version history'),
    });
  }

  closeHistory() {
    this.viewingHistoryFor.set(null);
    this.versionHistory.set([]);
  }

  // --- Upload new version ---
  openVersionUpload(doc: DocumentItem) {
    this.versioningFor.set(doc);
    this.newVersionFile.set(null);
    this.newVersionTagsInput.set(doc.tags.join(', '));
  }

  closeVersionUpload() {
    this.versioningFor.set(null);
    this.newVersionFile.set(null);
    this.newVersionTagsInput.set('');
  }

  onNewVersionFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.newVersionFile.set(input.files?.[0] ?? null);
  }

  submitNewVersion() {
    const doc = this.versioningFor();
    const file = this.newVersionFile();
    if (!doc || !file) return;

    const tags = this.newVersionTagsInput()
      .split(',')
      .map(t => t.trim())
      .filter(t => t.length > 0);

    this.docsService.uploadNewVersion(doc.id, file, tags).subscribe({
      next: () => {
        this.closeVersionUpload();
        this.loadDocuments();
      },
      error: err => this.errorMessage.set(err.error?.error ?? 'Failed to upload new version'),
    });
  }
}