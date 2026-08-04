import { Routes } from '@angular/router';
import { Login } from './features/auth/components/login/login';
import { Signup } from './features/auth/components/signup/signup';
import { Home } from './features/home/components/home';
import { UsersList } from './features/admin/components/users-list/users-list';
import { authGuard } from './core/guards/auth.guard';
import { DocumentsList } from './features/documents/components/documents-list/documents-list';
import { Profile } from './features/profile/components/profile';
import { VerifyEmail } from './features/auth/components/verify-email/verify-email';
import { ForgotPassword } from './features/auth/components/forgot-password/forgot-password';
import { ResetPassword } from './features/auth/components/reset-password/reset-password';

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
