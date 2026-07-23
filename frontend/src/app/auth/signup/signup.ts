import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../auth';

const RECAPTCHA_SITE_KEY = '6Lciv18tAAAAABrcPMJ6I1fEph9HHWbTSoZQLCT_';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './signup.html',
  styleUrl: '../auth.scss',
})
export class Signup {
  private fb = inject(FormBuilder);
  private auth = inject(Auth);
  private router = inject(Router);

  departments = ['HR', 'FINANCE', 'IT', 'LEGAL', 'MARKETING', 'OPERATIONS', 'OTHER'];
  genders = ['MALE', 'FEMALE', 'OTHER'];

  form: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    firstName: [''],
    lastName: [''],
    phoneNumber: [''],
    department: [''],
    gender: [''],
    age: [null],
  });

  errorMessage = signal<string | null>(null);
  showPassword = signal(false);
  signupSuccess = signal(false);
  submitting = signal(false);

  async onSubmit() {
    if (this.form.invalid) return;
    this.errorMessage.set(null);
    this.submitting.set(true);

    try {
      const token = await this.getRecaptchaToken();
      const payload = { ...this.form.value, recaptchaToken: token };

      this.auth.signup(payload).subscribe({
        next: () => {
          this.submitting.set(false);
          this.signupSuccess.set(true);
        },
        error: err => {
          this.submitting.set(false);
          this.errorMessage.set(err.error?.error ?? 'Something went wrong. Please try again.');
        },
      });
    } catch {
      this.submitting.set(false);
      this.errorMessage.set('Could not verify you are human. Please try again.');
    }
  }

  private getRecaptchaToken(): Promise<string> {
    return new Promise((resolve, reject) => {
      window.grecaptcha.ready(() => {
        window.grecaptcha
          .execute(RECAPTCHA_SITE_KEY, { action: 'signup' })
          .then(resolve)
          .catch(reject);
      });
    });
  }
}