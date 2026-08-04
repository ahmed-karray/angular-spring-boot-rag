import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../shared/models';

@Injectable({ providedIn: 'root' })
export class Admin {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8081/api/admin/users';

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  updateUser(id: number, data: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, data);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
