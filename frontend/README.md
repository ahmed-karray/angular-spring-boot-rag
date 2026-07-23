# 📄 Document Management System - Frontend

> A production-ready Angular 22 application featuring comprehensive document management, advanced authentication flows, user profile management, and role-based access control.

[![Angular](https://img.shields.io/badge/Angular-22-red?logo=angular)](https://angular.dev)
[![TypeScript](https://img.shields.io/badge/TypeScript-6.0-blue?logo=typescript)](https://www.typescriptlang.org/)
[![RxJS](https://img.shields.io/badge/RxJS-7.8-purple)](https://rxjs.dev)
[![Signals](https://img.shields.io/badge/Signals-Enabled-green)](https://angular.dev/guide/signals)

## ✨ Key Highlights

- 🎯 **100% Angular 22 Compliant** - Fully migrated to latest patterns and best practices
- 🔐 **Enterprise-Grade Security** - JWT authentication with comprehensive password management
- 📱 **Responsive Design** - Mobile-first, accessible UI components
- ⚡ **Modern State Management** - Angular Signals for reactive, type-safe state
- 🎨 **Custom Design System** - SCSS-based theming with animations
- 🧪 **Testing Ready** - Configured with Vitest for unit testing
- 🚀 **Production Optimized** - AOT compilation, tree-shaking, lazy loading

## 🚀 Features

### 🔒 Authentication & Authorization

- **User Registration** with comprehensive profile fields
  - Username, email, password with validation
  - Personal information (name, phone, age)
  - Department and gender selection
  - Google reCAPTCHA v2 integration for bot protection
  
- **Secure Login** system
  - JWT token-based authentication
  - Persistent session management
  - Automatic token refresh
  - Google reCAPTCHA v2 verification

- **Email Verification**
  - Token-based email confirmation
  - User activation workflow
  - Resend verification email option

- **Password Management**
  - Forgot password with email recovery
  - Secure password reset with token validation
  - Change password from profile
  - Password strength requirements (min 6 characters)
  - Password visibility toggles
  - Password match validation

- **Role-Based Access Control**
  - USER role: Standard access
  - ADMIN role: Full system management
  - Protected routes with auth guards
  - Automatic role-based UI rendering

### 👥 User Profile Management

- **View and Edit Profile**
  - Update username, email, personal details
  - Modify department and contact information
  - Real-time form validation
  - Success/error feedback

- **Password Change**
  - Current password verification
  - New password confirmation
  - Separate form with custom validators
  - Secure password update flow

### 👨‍💼 Admin Panel (Admin Only)

- **User Management Dashboard**
  - View all registered users in a table
  - Inline editing capabilities
  - Role assignment (USER/ADMIN)
  - User deletion with confirmation
  - Department and profile management
  - Real-time updates after modifications

### 📂 Document Management

- **Upload Documents**
  - File upload with validation
  - Tag-based organization
  - Department categorization
  - Automatic metadata tracking (uploader, date)

- **Advanced Search & Filtering**
  - Filter by document name
  - Filter by uploader
  - Date range filtering (from/to dates)
  - Department-based filtering
  - Tag-based search
  - Real-time debounced search (300ms)
  - Clear all filters option

- **Document Operations**
  - Download documents
  - Delete documents with confirmation
  - View document metadata
  - Tag display and management

### 🎨 Modern UI/UX

- **Responsive Design**
  - Mobile-first approach
  - Tablet and desktop optimized
  - Flexible grid layouts
  - Touch-friendly interactions

- **Accessibility**
  - WCAG compliant forms
  - Keyboard navigation support
  - Screen reader friendly
  - Focus management

- **Visual Feedback**
  - Loading states
  - Success/error messages
  - Form validation errors
  - Smooth transitions and animations
  - Interactive hover effects

- **Custom Styling**
  - Gradient backgrounds
  - Card-based layouts
  - Color-coded buttons
  - Custom form controls
  - Consistent spacing and typography

## 🏗️ Tech Stack & Dependencies

### Core Framework
- **Angular 22.0.0** - Latest Angular with signals and standalone components
- **TypeScript 6.0.2** - Type-safe development with latest features
- **RxJS 7.8.0** - Reactive programming for async operations

### Forms & Validation
- **@angular/forms** - Reactive forms with advanced validation
- Custom validators (password match, email format)
- Real-time field validation
- Form state management

### Routing & Navigation
- **@angular/router** - File-based routing with guards
- Lazy loading support
- Route protection with functional guards
- Navigation state management

### HTTP & API
- **@angular/common/http** - RESTful API communication
- HTTP interceptors for authentication
- Error handling and retry logic
- Request/response transformation

### Development Tools
- **Angular CLI 22.0.5** - Project scaffolding and build
- **Vitest 4.0.8** - Fast unit testing
- **Prettier 3.8.1** - Code formatting
- **JSDOM 28.0.0** - DOM testing utilities

### Security
- **Google reCAPTCHA v2** - Bot protection for forms
- JWT token management
- XSS protection
- CSRF mitigation

## 📦 Package Information

```json
{
  "name": "frontend",
  "version": "0.0.0",
  "packageManager": "npm@11.12.1"
}
```


## 📋 Angular 22 Migration - Complete

This project represents a **complete migration** to Angular 22 modern patterns and best practices.

### ✅ Migration Checklist

| Feature | Status | Implementation |
|---------|--------|----------------|
| **Dependency Injection** | ✅ Complete | All constructor injection replaced with `inject()` |
| **Signals** | ✅ Complete | Local state management using `signal()` |
| **RxJS → Signals** | ✅ Complete | Observable streams converted using `toSignal()` |
| **Computed State** | ✅ Complete | Derived values using `computed()` |
| **Standalone Components** | ✅ Complete | All 10 components are standalone |
| **Template Control Flow** | ✅ Complete | Using new `@if`, `@for`, `@else` syntax |
| **Functional Guards** | ✅ Complete | Auth guards using functional approach |
| **Effects** | ✅ Complete | Side effects managed with `effect()` |

### 📊 Migration Statistics

- **Total Components Migrated**: 10
  - Login, Signup, Home, Profile
  - Users List (Admin), Documents List
  - Verify Email, Forgot Password, Reset Password
  - App Root Component

- **Total Services Migrated**: 3
  - Auth Service
  - Admin Service  
  - Documents Service

- **Total Guards Migrated**: 1
  - Auth Guard (functional)

- **Total Templates Migrated**: 10
  - All using new control flow syntax

### 🔄 Migration Rules Applied

#### 1. Dependency Injection with `inject()`
**Before:**
```typescript
constructor(
  private auth: Auth,
  private router: Router,
  private fb: FormBuilder
) {}
```

**After:**
```typescript
private auth = inject(Auth);
private router = inject(Router);
private fb = inject(FormBuilder);
```

**Benefits:**
- Cleaner, more concise code
- Better testability
- Supports functional composition
- Type inference improvements

#### 2. Signals for Local State
**Before:**
```typescript
users: User[] = [];
loading = false;
errorMessage: string | null = null;

// Update
this.users = [...newUsers];
this.loading = true;
```

**After:**
```typescript
users = signal<User[]>([]);
loading = signal(false);
errorMessage = signal<string | null>(null);

// Update
this.users.set([...newUsers]);
this.loading.set(true);
this.users.update(prev => [...prev, newUser]);
```

**Benefits:**
- Fine-grained reactivity
- Automatic change detection
- Better performance
- Type safety

#### 3. RxJS to Signals with `toSignal()`
**Before:**
```typescript
ngOnInit() {
  this.auth.getMe().subscribe({
    next: user => this.isAdmin = user.role === 'ADMIN',
    error: () => this.isAdmin = false
  });
}
```

**After:**
```typescript
private currentUser = toSignal(this.auth.getMe(), { initialValue: null });
isAdmin = computed(() => this.currentUser()?.role === 'ADMIN');
```

**Benefits:**
- Declarative data flow
- Automatic subscription management
- No manual cleanup needed
- Composable with computed()

#### 4. Computed State
**Before:**
```typescript
get filteredUsers(): User[] {
  return this.users.filter(u => u.active);
}

get userCount(): number {
  return this.users.length;
}
```

**After:**
```typescript
filteredUsers = computed(() => 
  this.users().filter(u => u.active)
);

userCount = computed(() => 
  this.users().length
);
```

**Benefits:**
- Memoized computations
- Automatic dependency tracking
- Efficient re-rendering
- Clear data dependencies

#### 5. Template Control Flow
**Before:**
```html
<div *ngIf="loading">Loading...</div>
<div *ngIf="user; else noUser">{{ user.name }}</div>
<ng-template #noUser>No user found</ng-template>

<li *ngFor="let item of items; trackBy: trackById">
  {{ item.name }}
</li>

<ng-container [ngSwitch]="status">
  <span *ngSwitchCase="'active'">Active</span>
  <span *ngSwitchCase="'pending'">Pending</span>
  <span *ngSwitchDefault>Inactive</span>
</ng-container>
```

**After:**
```html
@if (loading()) {
  <div>Loading...</div>
} @else if (user()) {
  <div>{{ user().name }}</div>
} @else {
  <div>No user found</div>
}

@for (item of items(); track item.id) {
  <li>{{ item.name }}</li>
}

@switch (status()) {
  @case ('active') { <span>Active</span> }
  @case ('pending') { <span>Pending</span> }
  @default { <span>Inactive</span> }
}
```

**Benefits:**
- Built-in syntax (no imports needed)
- Better type inference
- Improved performance
- Cleaner, more readable code

#### 6. Functional Guards
**Before:**
```typescript
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: Auth, private router: Router) {}
  
  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
```

**After:**
```typescript
export const authGuard: CanActivateFn = (route, state) => {
  const auth = inject(Auth);
  const router = inject(Router);
  
  if (auth.isLoggedIn()) {
    return true;
  }
  
  router.navigate(['/login']);
  return false;
};
```

**Benefits:**
- Simpler implementation
- Better tree-shaking
- Functional composition
- Less boilerplate

#### 7. Effects for Side Effects
**Before:**
```typescript
ngOnChanges(changes: SimpleChanges) {
  if (changes['userId']) {
    this.analytics.track(this.userId);
  }
}
```

**After:**
```typescript
constructor() {
  effect(() => {
    const id = this.userId();
    if (id) {
      this.analytics.track(id);
    }
  });
}
```

**Benefits:**
- Declarative side effects
- Automatic dependency tracking
- Cleanup handled automatically
- Better composability

### 🎯 Best Practices Implemented

1. **Signal Usage Guidelines**
   - ✅ Use `signal()` for component local state
   - ✅ Use `computed()` for derived state
   - ✅ Use `toSignal()` for observables in templates
   - ✅ Keep complex RxJS pipelines as observables
   - ✅ Use `effect()` only for side effects, not state updates

2. **Form Management**
   - ✅ Keep `FormGroup` as regular properties (not signals)
   - ✅ Forms have their own change detection
   - ✅ Use signals for form submission state
   - ✅ Use signals for validation errors

3. **Performance Optimizations**
   - ✅ `track` expressions in `@for` loops
   - ✅ Computed values are memoized
   - ✅ Signals enable fine-grained updates
   - ✅ OnPush change detection compatible

## 🛠️ Development Setup

### Prerequisites

- **Node.js**: v18 or higher
- **npm**: v11.12.1 or higher  
- **Angular CLI**: v22.0.5 or higher

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd talan-angular/frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   - Backend API should run on `http://localhost:8081`
   - Update API endpoints if needed in service files

4. **Start development server**
   ```bash
   npm start
   # or
   ng serve
   ```

5. **Open application**
   - Navigate to `http://localhost:4200/`
   - Application will auto-reload on file changes

### Backend Connection

Ensure the Spring Boot backend is running before starting the frontend:

```bash
# In backend directory
./mvnw spring-boot:run
```

The backend should be accessible at `http://localhost:8081`

## 📁 Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── auth/                      # Authentication module
│   │   │   ├── login/                # Login component
│   │   │   │   ├── login.ts          # Component logic (signals, inject)
│   │   │   │   └── login.html        # Template (@if, @for)
│   │   │   ├── signup/               # Registration component
│   │   │   │   ├── signup.ts         # Component with form validation
│   │   │   │   ├── signup.html       # Template with reCAPTCHA
│   │   │   │   └── signup.scss       # Component styles
│   │   │   ├── verify-email/         # Email verification
│   │   │   ├── forgot-password/      # Password recovery
│   │   │   ├── reset-password/       # Password reset with token
│   │   │   ├── auth.ts               # Auth service (HTTP calls)
│   │   │   ├── auth.guard.ts         # Functional route guard
│   │   │   ├── auth.interceptor.ts   # JWT token interceptor
│   │   │   ├── auth.scss             # Shared auth styles
│   │   │   └── recaptcha.d.ts        # TypeScript declarations
│   │   ├── profile/                   # User profile management
│   │   │   ├── profile.ts            # Profile editing & password change
│   │   │   ├── profile.html          # Profile form template
│   │   │   └── profile.scss          # Profile styles
│   │   ├── admin/                     # Admin panel
│   │   │   ├── users-list/           # User management component
│   │   │   │   ├── users-list.ts     # Inline editing, delete users
│   │   │   │   ├── users-list.html   # Users table template
│   │   │   │   └── users-list.scss   # Table styles
│   │   │   ├── admin.ts              # Admin service
│   │   │   └── user.model.ts         # User interface/types
│   │   ├── documents/                 # Document management
│   │   │   ├── documents-list/       # Documents component
│   │   │   │   ├── documents-list.ts # Upload, search, filter
│   │   │   │   ├── documents-list.html # Documents template
│   │   │   │   └── documents-list.scss # Documents styles
│   │   │   ├── documents.ts          # Documents service
│   │   │   └── document.model.ts     # Document interface
│   │   ├── home/                      # Dashboard/home page
│   │   │   ├── home.ts               # Home component with role check
│   │   │   ├── home.html             # Dashboard template
│   │   │   └── home.scss             # Home styles
│   │   ├── app.ts                     # Root component
│   │   ├── app.html                   # Root template
│   │   ├── app.routes.ts             # Route configuration
│   │   └── app.config.ts             # App-level configuration
│   ├── styles/                        # Global styles
│   │   ├── _variables.scss           # SCSS variables (colors, spacing)
│   │   ├── _animations.scss          # Keyframe animations
│   │   ├── _buttons.scss             # Button styles
│   │   ├── _forms.scss               # Form control styles
│   │   └── styles.scss               # Global stylesheet
│   ├── index.html                     # HTML entry point
│   └── main.ts                        # TypeScript entry point
├── package.json                       # Dependencies and scripts
├── tsconfig.json                      # TypeScript configuration
├── angular.json                       # Angular CLI configuration
├── vitest.config.ts                   # Vitest test configuration
└── README.md                          # This file
```

### Component Architecture

Each component follows Angular 22 best practices:

1. **Standalone**: No NgModule required
2. **Signals**: Reactive state management
3. **Inject**: Modern dependency injection
4. **Templates**: New control flow syntax
5. **Type-safe**: Full TypeScript coverage


## 🎨 Styling Architecture

### Design System

The project implements a comprehensive SCSS-based design system:

#### Color Palette
```scss
// Primary colors (from _variables.scss)
--primary: #667eea      // Primary actions
--secondary: #764ba2    // Secondary elements
--accent: #f093fb       // Highlights and accents
--success: #48bb78      // Success states
--error: #f56565        // Error states
--warning: #ed8936      // Warning states
```

#### Typography
- **Font Family**: -apple-system, BlinkMacSystemFont, 'Segoe UI', system fonts
- **Font Sizes**: Responsive scaling from 14px to 32px
- **Font Weights**: 400 (normal), 500 (medium), 600 (semibold), 700 (bold)

#### Spacing System
- Base unit: 4px
- Scale: 4px, 8px, 12px, 16px, 24px, 32px, 48px, 64px
- Consistent across components

#### Component Styles

1. **Buttons** (`_buttons.scss`)
   - Primary, secondary, danger variants
   - Hover and active states
   - Disabled state styling
   - Icon button support
   - Loading state animations

2. **Forms** (`_forms.scss`)
   - Text inputs with focus states
   - Select dropdowns
   - Textareas
   - Radio buttons and checkboxes
   - Validation error styling
   - Label positioning

3. **Animations** (`_animations.scss`)
   - Fade in/out
   - Slide transitions
   - Pulse effects
   - Smooth hover transitions
   - Loading spinners

#### Responsive Breakpoints
```scss
$mobile: 480px;
$tablet: 768px;
$desktop: 1024px;
$wide: 1280px;
```

## 🔒 Security Features

### Authentication Security

1. **JWT Token Management**
   - Secure token storage in localStorage
   - Automatic token injection via HTTP interceptor
   - Token expiration handling
   - Refresh token support (if implemented)

2. **Password Security**
   - Minimum length validation (6 characters)
   - Password strength requirements
   - Password visibility toggles
   - Secure password reset flow
   - Current password verification for changes

3. **Route Protection**
   - Functional auth guards
   - Automatic redirect to login
   - Role-based route access
   - Navigation state preservation

4. **Bot Protection**
   - Google reCAPTCHA v2 integration
   - Form submission validation
   - Rate limiting (backend)

### API Security

1. **HTTP Interceptor**
   ```typescript
   // Automatic JWT token injection
   Authorization: Bearer <token>
   ```

2. **Error Handling**
   - Graceful error messages
   - 401/403 handling
   - Retry logic
   - Network failure handling

3. **XSS Protection**
   - Angular's built-in sanitization
   - Safe HTML rendering
   - Input validation

## 🔄 State Management Strategy

### Signals Architecture

```typescript
// Component State (local, encapsulated)
export class UsersList {
  users = signal<User[]>([]);           // Mutable state
  editingId = signal<number | null>(null);
  errorMessage = signal<string | null>(null);
  
  // Derived/computed state
  activeUsers = computed(() => 
    this.users().filter(u => u.active)
  );
  
  userCount = computed(() => 
    this.users().length
  );
}
```

### Observable Streams (for complex flows)

```typescript
// Search with debounce
this.filterForm.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(filters => this.service.search(filters))
).subscribe(results => this.results.set(results));
```

### Service State (shared across components)

```typescript
// Auth Service maintains user session
export class Auth {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();
  
  // Components consume via toSignal()
  // currentUser = toSignal(this.auth.currentUser$);
}
```

## 🚀 Building for Production

### Build Process

```bash
# Production build
ng build --configuration production

# Output location
dist/frontend/browser/

# Build artifacts
- index.html (entry point)
- main-[hash].js (application code)
- polyfills-[hash].js (browser polyfills)
- styles-[hash].css (compiled styles)
```

### Production Optimizations

1. **Ahead-of-Time (AOT) Compilation**
   - Templates compiled at build time
   - Faster rendering
   - Smaller bundle size

2. **Tree Shaking**
   - Unused code elimination
   - ES modules
   - Dead code removal

3. **Code Splitting**
   - Lazy loading routes
   - Vendor bundles
   - Initial bundle optimization

4. **Minification**
   - JavaScript minification
   - CSS minification
   - HTML minification

5. **Source Maps**
   - Production source maps (optional)
   - Error tracking
   - Debugging support

### Build Configuration

```json
{
  "optimization": true,
  "outputHashing": "all",
  "sourceMap": false,
  "extractCss": true,
  "namedChunks": false,
  "aot": true,
  "buildOptimizer": true
}
```

## 🧪 Testing Strategy

### Unit Testing with Vitest

```bash
# Run tests
npm test

# Run with coverage
ng test --coverage

# Watch mode
ng test --watch
```

### Test Structure

```typescript
import { describe, it, expect } from 'vitest';
import { TestBed } from '@angular/core/testing';

describe('AuthService', () => {
  it('should authenticate user', () => {
    const service = TestBed.inject(Auth);
    // test implementation
  });
});
```

### Testing Signals

```typescript
it('should update signal value', () => {
  const count = signal(0);
  expect(count()).toBe(0);
  
  count.set(5);
  expect(count()).toBe(5);
  
  count.update(n => n + 1);
  expect(count()).toBe(6);
});
```

## 📝 Available npm Scripts

| Script | Command | Description |
|--------|---------|-------------|
| **start** | `ng serve` | Start development server on port 4200 |
| **build** | `ng build` | Build project for development |
| **build:prod** | `ng build --configuration production` | Build for production |
| **watch** | `ng build --watch --configuration development` | Build with file watching |
| **test** | `ng test` | Run unit tests with Vitest |
| **test:coverage** | `ng test --coverage` | Run tests with coverage report |
| **lint** | `ng lint` | Lint TypeScript code |
| **format** | `prettier --write .` | Format code with Prettier |

## 🌐 API Integration

### Base URL Configuration

```typescript
// Default: http://localhost:8081
const API_BASE = 'http://localhost:8081/api';
```

### API Endpoints

#### Authentication
```typescript
POST   /api/auth/signup            // Register new user
POST   /api/auth/login             // User login
GET    /api/auth/me                // Get current user
GET    /api/auth/verify-email      // Verify email with token
POST   /api/auth/forgot-password   // Request password reset
POST   /api/auth/reset-password    // Reset password with token
PUT    /api/auth/profile           // Update user profile
```

#### Admin (Requires ADMIN role)
```typescript
GET    /api/admin/users            // Get all users
PUT    /api/admin/users/:id        // Update user
DELETE /api/admin/users/:id        // Delete user
```

#### Documents
```typescript
GET    /api/documents              // Get all documents (with filters)
POST   /api/documents              // Upload document
GET    /api/documents/:id          // Get document by ID
DELETE /api/documents/:id          // Delete document
GET    /api/documents/:id/download // Download document file
```

### Request/Response Examples

#### Login Request
```json
POST /api/auth/login
{
  "username": "john.doe",
  "password": "password123"
}
```

#### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "username": "john.doe",
    "email": "john@example.com",
    "role": "USER",
    "firstName": "John",
    "lastName": "Doe"
  }
}
```

#### Search Documents
```typescript
GET /api/documents?name=report&department=IT&fromDate=2024-01-01
```

## 📚 Additional Resources

### Angular Documentation
- [Angular Official Docs](https://angular.dev) - Complete Angular documentation
- [Angular CLI Reference](https://angular.dev/tools/cli) - CLI command reference
- [Angular Signals Guide](https://angular.dev/guide/signals) - Signals deep dive
- [Angular Forms](https://angular.dev/guide/forms) - Reactive forms guide
- [Angular Router](https://angular.dev/guide/routing) - Routing and navigation

### TypeScript
- [TypeScript Handbook](https://www.typescriptlang.org/docs/) - TypeScript documentation
- [TypeScript Playground](https://www.typescriptlang.org/play) - Online TypeScript editor

### RxJS
- [RxJS Documentation](https://rxjs.dev) - RxJS operators and concepts
- [RxJS Operator Decision Tree](https://rxjs.dev/operator-decision-tree) - Choose the right operator

### Testing
- [Vitest Documentation](https://vitest.dev) - Vitest testing framework
- [Angular Testing Guide](https://angular.dev/guide/testing) - Angular testing patterns

### Tools
- [Prettier](https://prettier.io) - Code formatting
- [ESLint](https://eslint.org) - JavaScript/TypeScript linting

## 👥 User Roles & Permissions

### USER Role
✅ **Allowed:**
- View home dashboard
- View and edit own profile
- Change own password
- Upload documents
- View own documents
- Search and filter documents
- Download documents
- Delete own documents

❌ **Restricted:**
- Access admin panel
- View all users
- Edit other users
- Delete other users
- Manage user roles

### ADMIN Role
✅ **Full Access:**
- All USER permissions
- Access admin panel
- View all registered users
- Edit any user profile
- Change user roles (USER ↔ ADMIN)
- Delete users
- Manage departments
- View system-wide documents
- Manage all documents

## 🔄 Authentication Flow Diagram

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │
       │ 1. Login Request
       ▼
┌─────────────────┐
│  Login Component│
└────────┬────────┘
         │ 2. Call Auth Service
         ▼
┌─────────────────┐
│   Auth Service  │
└────────┬────────┘
         │ 3. HTTP POST /api/auth/login
         ▼
┌─────────────────┐
│ Backend API     │
└────────┬────────┘
         │ 4. Return JWT Token
         ▼
┌─────────────────┐
│  Auth Service   │◄── Store token in localStorage
└────────┬────────┘
         │ 5. Navigate to /home
         ▼
┌─────────────────┐
│  Home Component │
└────────┬────────┘
         │ 6. Auth Guard checks token
         ▼
┌─────────────────┐
│  Auth Guard     │
└────────┬────────┘
         │ 7. Allow access
         ▼
┌─────────────────┐
│  Render Home    │
└─────────────────┘

All subsequent API calls include:
Authorization: Bearer <token>
(via HTTP Interceptor)
```

## 🐛 Troubleshooting

### Common Issues

#### 1. CORS Errors
**Problem:** Browser blocks requests to backend
**Solution:** 
```java
// Backend: Configure CORS in Spring Boot
@CrossOrigin(origins = "http://localhost:4200")
```

#### 2. Auth Token Not Sent
**Problem:** API returns 401 Unauthorized
**Solution:** 
- Check token in localStorage: `localStorage.getItem('token')`
- Verify HTTP interceptor is registered in app.config.ts
- Ensure token format: `Bearer <token>`

#### 3. Signals Not Updating Template
**Problem:** Template doesn't reflect signal changes
**Solution:**
- Call signals as functions: `{{ count() }}` not `{{ count }}`
- Use `set()` or `update()` to modify signals
- Check signal is declared correctly

#### 4. ReCAPTCHA Not Loading
**Problem:** reCAPTCHA widget doesn't appear
**Solution:**
- Verify script tag in index.html
- Check site key configuration
- Ensure internet connection
- Check browser console for errors

#### 5. Build Errors
**Problem:** Production build fails
**Solution:**
```bash
# Clear cache
rm -rf node_modules dist .angular
npm install
ng build --configuration production
```

## 📄 License

This project is developed as part of a document management system demonstration. 

**Copyright © 2024**

For educational and demonstration purposes.

## 🤝 Contributing

### Development Workflow

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Standards

- Follow Angular style guide
- Use Prettier for formatting
- Write unit tests for new features
- Use TypeScript strict mode
- Add JSDoc comments for public APIs
- Follow Angular 22 patterns (signals, inject, new control flow)

### Commit Messages

```
feat: Add password reset functionality
fix: Resolve document upload error
docs: Update README with API endpoints
style: Format code with Prettier
refactor: Convert to Angular signals
test: Add unit tests for auth service
```

## 📞 Support

For issues, questions, or contributions:

- Create an issue in the repository
- Contact the development team
- Check documentation and resources above

---

**Built with ❤️ using Angular 22 and modern web technologies**

Last Updated: 2024
