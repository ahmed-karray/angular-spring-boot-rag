import { Component, inject, OnInit, signal } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Auth } from '../auth/auth';

function passwordsMatchValidator(): ValidatorFn {
  return (group: AbstractControl): ValidationErrors | null => {
    const newPassword = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { passwordMismatch: true };
  };
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './profile.html',
  styleUrl: './profile.scss',
})
export class Profile implements OnInit {
  private auth = inject(Auth);
  private fb = inject(FormBuilder);

  departments = ['HR', 'FINANCE', 'IT', 'LEGAL', 'MARKETING', 'OPERATIONS', 'OTHER'];
  genders = ['MALE', 'FEMALE', 'OTHER'];

  profileError = signal<string | null>(null);
  profileSuccess = signal<string | null>(null);
  passwordError = signal<string | null>(null);
  passwordSuccess = signal<string | null>(null);

  showCurrentPassword = signal(false);
  showNewPassword = signal(false);
  showConfirmPassword = signal(false);

  profileForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    firstName: [''],
    lastName: [''],
    phoneNumber: [''],
    department: [''],
    gender: [''],
    age: [null],
  });

  passwordForm: FormGroup = this.fb.group(
    {
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
    },
    { validators: passwordsMatchValidator() }
  );

  ngOnInit() {
    this.auth.getMe().subscribe({
      next: user => {
        this.profileForm.patchValue({
          username: user.username,
          email: user.email,
          firstName: user.firstName ?? '',
          lastName: user.lastName ?? '',
          phoneNumber: user.phoneNumber ?? '',
          department: user.department ?? '',
          gender: user.gender ?? '',
          age: user.age,
        });
      },
      error: err => this.profileError.set(err.error?.error ?? 'Failed to load profile'),
    });
  }

  saveProfile() {
    if (this.profileForm.invalid) return;
    this.profileError.set(null);
    this.profileSuccess.set(null);

    this.auth.updateProfile(this.profileForm.value).subscribe({
      next: () => this.profileSuccess.set('Profile updated successfully'),
      error: err => this.profileError.set(err.error?.error ?? 'Failed to update profile'),
    });
  }

  changePassword() {
    if (this.passwordForm.invalid) return;

    const { currentPassword, newPassword } = this.passwordForm.value;

    this.passwordError.set(null);
    this.passwordSuccess.set(null);

    this.auth.updateProfile({ currentPassword, newPassword }).subscribe({
      next: () => {
        this.passwordSuccess.set('Password changed successfully');
        this.passwordForm.reset();
      },
      error: err => this.passwordError.set(err.error?.error ?? 'Failed to change password'),
    });
  }
}