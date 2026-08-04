export interface User {
  id: number;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN';
  firstName: string | null;
  lastName: string | null;
  phoneNumber: string | null;
  department: string | null;
  gender: string | null;
  age: number | null;
  createdAt: string;
}

export interface CurrentUser {
  id: number;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN';
  firstName: string | null;
  lastName: string | null;
  phoneNumber: string | null;
  department: string | null;
  gender: string | null;
  age: number | null;
  createdAt: string;
}

export interface AuthResponse {
  token: string;
}
