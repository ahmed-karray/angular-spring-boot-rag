import { Component, inject, signal } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth.service';

function passwordsMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const newPassword = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { passwordMismatch: true };
  };
}

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.scss',
})
export class ResetPassword {
  private fb = inject(FormBuilder);
  private auth = inject(Auth);
  private route = inject(ActivatedRoute);

  form: FormGroup = this.fb.group(
    {
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
    },
    { validators: passwordsMatchValidator() }
  );

  showNewPassword = signal(false);
  showConfirmPassword = signal(false);
  status = signal<'idle' | 'success' | 'error'>('idle');
  errorMessage = signal<string | null>(null);

  onSubmit() {
    if (this.form.invalid) return;

    const token = this.route.snapshot.queryParamMap.get('token');
    if (!token) {
      this.status.set('error');
      this.errorMessage.set('Missing reset token.');
      return;
    }

    this.auth.resetPassword(token, this.form.value.newPassword).subscribe({
      next: () => this.status.set('success'),
      error: err => {
        this.status.set('error');
        this.errorMessage.set(err.error?.error ?? 'Failed to reset password. The link may be invalid or expired.');
      },
    });
  }
}