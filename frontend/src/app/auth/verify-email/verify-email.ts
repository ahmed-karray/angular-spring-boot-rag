import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './verify-email.html',
  styleUrl: '../auth.scss',
})
export class VerifyEmail {
  private route = inject(ActivatedRoute);
  private http = inject(HttpClient);

  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');
  errorMessage = signal<string | null>(null);

  confirmEmail() {
    const token = this.route.snapshot.queryParamMap.get('token');

    if (!token) {
      this.status.set('error');
      this.errorMessage.set('Missing verification token.');
      return;
    }

    this.status.set('loading');

    this.http.get('http://localhost:8081/api/auth/verify-email', { params: { token } }).subscribe({
      next: () => this.status.set('success'),
      error: err => {
        this.status.set('error');
        this.errorMessage.set(err.error?.error ?? 'Verification failed. The link may be invalid or expired.');
      },
    });
  }
}