import { Component, inject, computed } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { Auth } from '../../auth/services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  private auth = inject(Auth);
  private router = inject(Router);

  currentUser = toSignal(this.auth.getMe(), { initialValue: null });
  isAdmin = computed(() => this.currentUser()?.role === 'ADMIN');

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}