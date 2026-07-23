import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Admin } from '../admin';
import { User } from '../user.model';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './users-list.html',
  styleUrl: './users-list.scss',
})
export class UsersList implements OnInit {
  private admin = inject(Admin);

  users = signal<User[]>([]);
  errorMessage = signal<string | null>(null);
  editingId = signal<number | null>(null);

  departments = ['HR', 'FINANCE', 'IT', 'LEGAL', 'MARKETING', 'OPERATIONS', 'OTHER'];
  genders = ['MALE', 'FEMALE', 'OTHER'];

  editForm: {
    username: string;
    email: string;
    role: 'USER' | 'ADMIN';
    firstName: string;
    lastName: string;
    phoneNumber: string;
    department: string;
    gender: string;
    age: number | null;
  } = {
    username: '',
    email: '',
    role: 'USER',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    department: '',
    gender: '',
    age: null,
  };

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.admin.getAllUsers().subscribe({
      next: users => this.users.set(users),
      error: err => this.errorMessage.set(err.error?.error ?? 'Failed to load users'),
    });
  }

  startEdit(user: User) {
    this.editingId.set(user.id);
    this.editForm = {
      username: user.username,
      email: user.email,
      role: user.role,
      firstName: user.firstName ?? '',
      lastName: user.lastName ?? '',
      phoneNumber: user.phoneNumber ?? '',
      department: user.department ?? '',
      gender: user.gender ?? '',
      age: user.age,
    };
  }

  cancelEdit() {
    this.editingId.set(null);
  }

  saveEdit(id: number) {
    this.admin.updateUser(id, this.editForm).subscribe({
      next: () => {
        this.editingId.set(null);
        this.loadUsers();
      },
      error: err => this.errorMessage.set(err.error?.error ?? 'Failed to update user'),
    });
  }

  deleteUser(id: number) {
    if (!confirm('Delete this user?')) return;

    this.admin.deleteUser(id).subscribe({
      next: () => this.loadUsers(),
      error: err => this.errorMessage.set(err.error?.error ?? 'Failed to delete user'),
    });
  }
}