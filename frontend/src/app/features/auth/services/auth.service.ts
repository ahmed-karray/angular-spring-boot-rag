import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthResponse, CurrentUser } from '../../../shared/models';

@Injectable({ providedIn: 'root' }) 
export class Auth {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8081/api/auth';
  private tokenKey = 'auth_token';

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.baseUrl}/login`, { username, password })
      .pipe(tap(res => this.setToken(res.token)));
  }

  signup(data: {
  username: string;
  email: string;
  password: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  department?: string;
  gender?: string;
  age?: number;
}): Observable<AuthResponse> {
  return this.http
    .post<AuthResponse>(`${this.baseUrl}/signup`, data)
    .pipe(tap(res => this.setToken(res.token)));
}

  getMe(): Observable<CurrentUser> {
    return this.http.get<CurrentUser>(`${this.baseUrl}/me`);
  }

  setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }
  updateProfile(data: {
  username?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  department?: string;
  gender?: string;
  age?: number | null;
  newPassword?: string;
  currentPassword?: string;
}): Observable<CurrentUser> {
  return this.http.put<CurrentUser>(`${this.baseUrl}/me`, data);
}

forgotPassword(email: string): Observable<void> {
  return this.http.post<void>(`${this.baseUrl}/forgot-password`, { email });
}

resetPassword(token: string, newPassword: string): Observable<void> {
  return this.http.post<void>(`${this.baseUrl}/reset-password`, { token, newPassword });
}
}
