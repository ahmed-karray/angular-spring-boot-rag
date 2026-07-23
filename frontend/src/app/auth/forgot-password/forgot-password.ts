import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './forgot-password.html',
  styleUrl: '../auth.scss',
})
export class ForgotPassword {
  private fb = inject(FormBuilder);
  private auth = inject(Auth);

  form: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
  });

  submitted = signal(false);
  errorMessage = signal<string | null>(null);

  onSubmit() {
    if (this.form.invalid) return;
    this.errorMessage.set(null);

    this.auth.forgotPassword(this.form.value.email).subscribe({
      next: () => this.submitted.set(true),
      error: err => this.errorMessage.set(err.error?.error ?? 'Something went wrong. Please try again.'),
    });
  }
}