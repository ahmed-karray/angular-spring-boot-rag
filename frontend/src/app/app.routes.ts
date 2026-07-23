import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Signup } from './auth/signup/signup';
import { Home } from './home/home';
import { UsersList } from './admin/users-list/users-list';
import { authGuard } from './auth/auth.guard';
import { DocumentsList } from './documents/documents-list/documents-list';
import { Profile } from './profile/profile';
import { VerifyEmail } from './auth/verify-email/verify-email';
import { ForgotPassword } from './auth/forgot-password/forgot-password';
import { ResetPassword } from './auth/reset-password/reset-password';


export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'signup', component: Signup },
  { path: 'home', component: Home, canActivate: [authGuard] },
  { path: 'admin/users', component: UsersList, canActivate: [authGuard] },
  { path: 'documents', component: DocumentsList, canActivate: [authGuard] },
  { path: 'profile', component: Profile, canActivate: [authGuard] },
  { path: 'verify-email', component: VerifyEmail },
  { path: 'forgot-password', component: ForgotPassword },
  { path: 'reset-password', component: ResetPassword },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
