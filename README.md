# 📄 Document Management System with RAG - Full Stack Application

> A production-ready full-stack document management system with AI-powered PDF
> text extraction built with **Angular 22** (frontend) and **Spring Boot**
> (backend)

[![Angular](https://img.shields.io/badge/Angular-22-red?logo=angular)](https://angular.dev)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-green?logo=spring)](https://spring.io)
[![TypeScript](https://img.shields.io/badge/TypeScript-6.0-blue?logo=typescript)](https://www.typescriptlang.org/)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.java.com)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org)
[![Security](https://img.shields.io/badge/Security-9.5%2F10-brightgreen)](./backend/SECURITY_STATUS_FINAL.md)

## 🌟 Project Overview

A production-ready document management system with RAG (Retrieval-Augmented
Generation) capabilities featuring:

- **🔐 Secure Authentication** - JWT-based auth with email verification,
  password reset, and reCAPTCHA
- **👥 Role-Based Access Control** - USER and ADMIN roles with protected routes
- **📂 Document Management** - Upload, download, search, organize, version
  control, and sharing
- **🤖 PDF Text Extraction** - Three extraction methods (PDFBox, Apache Tika,
  Poppler) with performance comparison
- **👨‍💼 User Management** - Admin panel for managing users, roles, and
  departments
- **🎨 Modern Architecture** - Angular 22 with Signals + Spring Boot 4.1 REST
  API
- **🔒 Production Security** - 9.5/10 security score with environment variable
  protection
- **🚀 Deployment Ready** - Complete deployment guides for AWS, Azure, GCP,
  Heroku, Docker

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Browser (Port 4200)                │
│                                                      │
│  ┌────────────────────────────────────────────┐    │
│  │        Angular 22 Frontend                  │    │
│  │  - Signals & Standalone Components          │    │
│  │  - JWT Authentication                       │    │
│  │  - Reactive Forms                           │    │
│  │  - HTTP Interceptors                        │    │
│  └─────────────────┬──────────────────────────┘    │
└────────────────────┼───────────────────────────────┘
                     │
                     │ HTTP/REST API
                     │ (JSON)
                     │
┌────────────────────▼───────────────────────────────┐
│              Spring Boot Backend (Port 8080)        │
│                                                     │
│  ┌─────────────────────────────────────────────┐  │
│  │  REST Controllers                            │  │
│  │  - Authentication & Authorization            │  │
│  │  - User Management                           │  │
│  │  - Document Management                       │  │
│  └────────────┬────────────────────────────────┘  │
│               │                                    │
│  ┌────────────▼────────────────────────────────┐  │
│  │  Service Layer                               │  │
│  │  - Business Logic                            │  │
│  │  - JWT Token Management                      │  │
│  │  - File Storage                              │  │
│  └────────────┬────────────────────────────────┘  │
│               │                                    │
│  ┌────────────▼────────────────────────────────┐  │
│  │  Data Access Layer (JPA/Hibernate)          │  │
│  └────────────┬────────────────────────────────┘  │
│               │                                    │
│  ┌────────────▼────────────────────────────────┐  │
│  │  Database (PostgreSQL)                          │  │
│  └─────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
```

## 🚀 Quick Start Guide

**⚡ Want to start immediately?** See [QUICK_START.md](./QUICK_START.md) for a
5-minute setup guide!

### Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** v18 or higher ([Download](https://nodejs.org/))
- **npm** v11 or higher (comes with Node.js)
- **Java JDK** 21
  ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven** 3.6+ (or use included Maven wrapper `mvnw`)
- **PostgreSQL** 15+ ([Download](https://www.postgresql.org/download/))
- **Git** ([Download](https://git-scm.com/))

### Installation Steps

#### 1. Clone the Repository

```bash
git clone <repository-url>
cd angular-spring-boot-rag
```

#### 2. Backend Setup (Spring Boot)

```bash
# Navigate to backend directory
cd backend

# Create .env file with your secrets
cp .env.example .env
# Edit .env and add your credentials

# Install dependencies and build (Windows)
.\mvnw.cmd clean install

# Or on Linux/Mac
./mvnw clean install

# Configure PostgreSQL database
# Create database: createdb authdb
# Or using psql: CREATE DATABASE authdb;

# Run the backend server
.\mvnw.cmd spring-boot:run
# Or: ./mvnw spring-boot:run (Linux/Mac)
```

✅ **Backend should now be running on:** `http://localhost:8081`

**Important:** Make sure your `.env` file contains:

```env
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=generate_with_openssl_rand_base64_32
EMAIL_USERNAME=your_email@gmail.com
EMAIL_APP_PASSWORD=your_gmail_app_password
RECAPTCHA_SECRET_KEY=your_recaptcha_secret
```

> **Generate JWT Secret:** `openssl rand -base64 32` (Linux/Mac/Git Bash)
>
> **Windows PowerShell:**
>
> ```powershell
> $bytes = New-Object byte[] 32
> [System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
> [Convert]::ToBase64String($bytes)
> ```

#### 3. Frontend Setup (Angular)

Open a **new terminal window** (keep backend running):

```bash
# Navigate to frontend directory (from project root)
cd frontend

# Install dependencies
npm install

# Start the development server
npm start
```

✅ **Frontend should now be running on:** `http://localhost:4200`

### 4. Access the Application

Open your browser and navigate to:

```
http://localhost:4200
```

You should see the **Login page**. The application is now ready to use!

## 📋 Quick Test

### Create Your First Account

1. Click **"Sign Up"** on the login page
2. Fill in the registration form:
   - Username: `admin`
   - Email: `admin@example.com`
   - Password: `password123`
   - Fill other required fields
3. Complete the reCAPTCHA
4. Click **"Sign Up"**
5. Login with your credentials

## 🛠️ Development Workflow

### Running Both Services Simultaneously

**Option 1: Multiple Terminal Windows**

```bash
# Terminal 1 - Backend
cd backend
.\mvnw.cmd spring-boot:run

# Terminal 2 - Frontend
cd frontend
npm start
```

**Option 2: Background Processes (Windows PowerShell)**

```powershell
# Start backend in background
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd backend; .\mvnw.cmd spring-boot:run"

# Start frontend in background
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd frontend; npm start"
```

**Option 3: Use an IDE**

- **IntelliJ IDEA**: Open both backend and frontend, run configurations
  separately
- **VS Code**: Use multiple terminals or tasks

### Making Changes

**Frontend Changes:**

- Edit files in `frontend/src/`
- Changes auto-reload in the browser (hot reload)
- See changes instantly at `http://localhost:4200`

**Backend Changes:**

- Edit files in `backend/src/`
- Stop the server (Ctrl+C)
- Rebuild: `.\mvnw.cmd clean install`
- Restart: `.\mvnw.cmd spring-boot:run`
- Or use Spring Boot DevTools for auto-restart

## 📁 Project Structure

```
angular-spring-boot-rag/
├── backend/                        # Spring Boot Application (Java 21)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/backend/
│   │   │   │   ├── auth/              # Authentication controllers & services
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   └── Auth*.java     # Request/Response DTOs
│   │   │   │   ├── user/              # User management
│   │   │   │   │   ├── controller/    # User REST controllers
│   │   │   │   │   ├── entity/        # User, Department, Role entities
│   │   │   │   │   ├── repository/    # JPA repositories
│   │   │   │   │   └── service/       # User business logic
│   │   │   │   ├── document/          # Document management
│   │   │   │   │   ├── controller/    # Document REST controllers
│   │   │   │   │   ├── entity/        # Document, Tag entities
│   │   │   │   │   ├── repository/    # Document repositories with specifications
│   │   │   │   │   ├── service/       # Document business logic
│   │   │   │   │   ├── dto/           # Document DTOs
│   │   │   │   │   └── mapper/        # Entity-DTO mappers
│   │   │   │   ├── security/          # JWT & security config
│   │   │   │   │   ├── JwtService.java       # JWT token generation/validation
│   │   │   │   │   ├── JwtAuthFilter.java    # JWT authentication filter
│   │   │   │   │   └── RecaptchaService.java # reCAPTCHA validation
│   │   │   │   ├── config/            # Application configuration
│   │   │   │   │   └── SecurityConfig.java   # Spring Security setup
│   │   │   │   ├── email/             # Email service
│   │   │   │   │   └── EmailService.java     # SMTP email sending
│   │   │   │   └── exception/         # Global exception handling
│   │   │   │       └── GlobalExceptionHandler.java
│   │   │   └── resources/
│   │   │       ├── application.properties    # Backend configuration
│   │   │       └── db/migration/             # Flyway database migrations
│   │   └── test/                      # Backend tests
│   ├── pom.xml                        # Maven dependencies
│   ├── .env                           # Environment variables (not in Git)
│   ├── .env.example                   # Environment variables template
│   ├── SETUP.md                       # Backend setup guide
│   ├── PDF_EXTRACTION_COMPARISON.md   # PDF extraction technical comparison
│   ├── SECURITY_STATUS_FINAL.md       # Security assessment report
│   ├── PRODUCTION_DEPLOYMENT.md       # Deployment guide (7 platforms)
│   ├── deploy-to-server.sh            # Automated deployment script
│   ├── setup-production.sh            # Production environment setup
│   └── mvnw.cmd / mvnw               # Maven wrapper
│
├── frontend/                       # Angular 22 Application
│   ├── src/
│   │   ├── app/
│   │   │   ├── core/                  # Core module (guards, interceptors)
│   │   │   │   ├── guards/            # Auth guards
│   │   │   │   └── interceptors/      # HTTP interceptors
│   │   │   ├── shared/                # Shared resources
│   │   │   │   └── models/            # TypeScript interfaces/types
│   │   │   ├── features/              # Feature modules
│   │   │   │   ├── auth/              # Authentication feature
│   │   │   │   │   └── components/    # Login, signup, verify, reset password
│   │   │   │   ├── documents/         # Document management feature
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── documents-list/  # Main documents component
│   │   │   │   │   │   └── extraction-comparison/ # PDF extraction comparison UI
│   │   │   │   │   └── services/      # Document services
│   │   │   │   ├── admin/             # Admin feature
│   │   │   │   │   └── components/    # User management
│   │   │   │   ├── profile/           # User profile feature
│   │   │   │   │   └── components/    # Profile editing
│   │   │   │   └── home/              # Dashboard feature
│   │   │   │       └── components/    # Home component
│   │   │   ├── layout/                # Layout components (future)
│   │   │   ├── app.component.ts       # Root component
│   │   │   ├── app.routes.ts          # Route configuration
│   │   │   └── app.config.ts          # App-level configuration
│   │   ├── styles/                    # Global SCSS styles
│   │   │   ├── _variables.scss        # Design tokens (colors, spacing)
│   │   │   ├── _animations.scss       # CSS animations
│   │   │   ├── _buttons.scss          # Button styles
│   │   │   ├── _forms.scss            # Form control styles
│   │   │   ├── _mixins.scss           # SCSS mixins
│   │   │   ├── _tables.scss           # Table styles
│   │   │   └── styles.scss            # Main stylesheet
│   │   ├── index.html                 # HTML entry point
│   │   └── main.ts                    # TypeScript entry point
│   ├── package.json                   # npm dependencies
│   ├── angular.json                   # Angular CLI configuration
│   ├── tsconfig.json                  # TypeScript configuration
│   ├── vitest.config.ts               # Vitest test configuration
│   └── README.md                      # Frontend documentation
│
├── README.md                          # This file (main documentation)
├── .gitignore                         # Git ignore rules
└── .env                               # Root environment file (optional)
```

### Key Directories Explained

- **`backend/src/main/java`** - All Java source code organized by feature
- **`backend/src/main/resources`** - Configuration files and database migrations
- **`frontend/src/app/features`** - Feature-based architecture (Angular best
  practice)
- **`frontend/src/app/core`** - Singleton services, guards, interceptors
- **`frontend/src/app/shared`** - Reusable components, models, utilities
- **`frontend/src/styles`** - Global design system (SCSS)

## 🔗 API Endpoints

### Base URL: `http://localhost:8081/api`

#### Authentication Endpoints

| Method   | Endpoint                       | Description            | Auth Required |
| -------- | ------------------------------ | ---------------------- | ------------- |
| **POST** | `/auth/signup`                 | Register new user      | No            |
| **POST** | `/auth/login`                  | User login             | No            |
| **GET**  | `/auth/me`                     | Get current user       | Yes           |
| **GET**  | `/auth/verify-email?token=xxx` | Verify email           | No            |
| **POST** | `/auth/forgot-password`        | Request password reset | No            |
| **POST** | `/auth/reset-password`         | Reset password         | No            |
| **PUT**  | `/auth/profile`                | Update profile         | Yes           |

#### Admin Endpoints (ADMIN role required)

| Method     | Endpoint           | Description   | Auth Required |
| ---------- | ------------------ | ------------- | ------------- |
| **GET**    | `/admin/users`     | Get all users | Admin only    |
| **PUT**    | `/admin/users/:id` | Update user   | Admin only    |
| **DELETE** | `/admin/users/:id` | Delete user   | Admin only    |

#### Document Endpoints

| Method     | Endpoint                      | Description                    | Auth Required |
| ---------- | ----------------------------- | ------------------------------ | ------------- |
| **GET**    | `/documents`                  | Search documents               | Yes           |
| **POST**   | `/documents`                  | Upload document                | Yes           |
| **GET**    | `/documents/tags`             | Get all tags                   | Yes           |
| **GET**    | `/documents/:id`              | Get document                   | Yes           |
| **DELETE** | `/documents/:id`              | Delete document                | Yes           |
| **GET**    | `/documents/:id/download`     | Download file                  | Yes           |
| **POST**   | `/documents/:id/versions`     | Upload new version             | Yes           |
| **GET**    | `/documents/:id/versions`     | Get version history            | Yes           |
| **PUT**    | `/documents/:id/sharing`      | Update sharing settings        | Yes           |
| **GET**    | `/documents/:id/text/compare` | Compare PDF extraction methods | Yes           |

#### Document Search Parameters

```http
GET /api/documents?name=report&department=IT&fromDate=2024-01-01&toDate=2024-12-31&uploadedBy=john&tag=important
```

| Parameter    | Type   | Description                                      |
| ------------ | ------ | ------------------------------------------------ |
| `name`       | string | Filter by document name (partial match)          |
| `fromDate`   | date   | Documents uploaded after this date (YYYY-MM-DD)  |
| `toDate`     | date   | Documents uploaded before this date (YYYY-MM-DD) |
| `department` | string | Filter by uploader's department                  |
| `uploadedBy` | string | Filter by uploader username (partial match)      |
| `tag`        | string | Filter by tag name                               |

## 🧪 Testing

### Backend Tests

```bash
cd backend
.\mvnw.cmd test

# With coverage
.\mvnw.cmd test jacoco:report
```

### Frontend Tests

```bash
cd frontend
npm test

# With coverage
npm run test:coverage
```

## 📦 Building for Production

### Backend Build

```bash
cd backend
.\mvnw.cmd clean package

# JAR file will be in: target/backend-0.0.1-SNAPSHOT.jar

# Run production JAR
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend Build

```bash
cd frontend
npm run build

# Production files will be in: dist/frontend/browser/

# Deploy to web server (nginx, Apache, etc.)
```

## 🚢 Deployment Options

### 📖 Complete Deployment Guide

**See [PRODUCTION_DEPLOYMENT.md](./backend/PRODUCTION_DEPLOYMENT.md) for
comprehensive deployment instructions covering:**

1. **Traditional Linux Server** (Ubuntu/Debian)
   - systemd service configuration
   - Environment variable management
   - Automated deployment script

2. **Docker & Docker Compose**
   - Complete Dockerfile examples
   - Multi-container setup with PostgreSQL
   - Environment variable configuration

3. **AWS (Amazon Web Services)**
   - Elastic Beanstalk
   - EC2 with Systems Manager Parameter Store
   - ECS (Elastic Container Service)

4. **Heroku**
   - CLI and dashboard setup
   - Config vars management
   - One-command deployment

5. **Azure (Microsoft)**
   - App Service deployment
   - Key Vault for secrets
   - Azure CLI examples

6. **Google Cloud Platform (GCP)**
   - Cloud Run deployment
   - Secret Manager integration
   - Compute Engine setup

7. **DigitalOcean**
   - App Platform
   - Droplet configuration

### Quick Start Scripts

**Setup Production Environment:**

```bash
# On your production server
./backend/setup-production.sh
```

**Deploy from Local Machine:**

```bash
# Automated deployment to server
./backend/deploy-to-server.sh
```

### Frontend Deployment

**Option 1: Vercel** (Recommended)

```bash
cd frontend
npm install -g vercel
vercel
```

**Option 2: Netlify**

```bash
npm run build
# Deploy dist/frontend/browser/
```

**Option 3: Serve from Backend**

```bash
# Build frontend
cd frontend
npm run build

# Copy to backend static resources
cp -r dist/frontend/browser/* ../backend/src/main/resources/static/

# Deploy backend JAR (includes frontend)
cd ../backend
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Environment Variables Setup

All platforms require these 6 environment variables:

```bash
DB_USERNAME=your_production_db_user
DB_PASSWORD=your_production_db_password
JWT_SECRET=your_production_jwt_secret_256bit
EMAIL_USERNAME=your_production_email@domain.com
EMAIL_APP_PASSWORD=your_production_email_password
RECAPTCHA_SECRET_KEY=your_production_recaptcha_key
```

**Generate production secrets:**

```bash
# JWT Secret (256-bit)
openssl rand -base64 32

# Database Password (strong)
openssl rand -base64 24
```

**Windows PowerShell:**

```powershell
# Generate JWT Secret
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
[Convert]::ToBase64String($bytes)
```

### Security Checklist for Deployment

- [ ] Generate new production secrets (never use development secrets)
- [ ] Set up HTTPS/TLS certificate
- [ ] Update CORS in `SecurityConfig.java` to production domain
- [ ] Configure firewall rules (allow 80/443, block 8081 externally)
- [ ] Set up database backups
- [ ] Configure log rotation
- [ ] Set up monitoring and alerts
- [ ] Restrict SSH access (key-based auth, disable root)
- [ ] Set proper file permissions (`.env` should be 600)
- [ ] Review and test disaster recovery plan

---

## 🔧 Configuration

### Backend Configuration

The backend uses environment variables for all sensitive configuration. Create a
`.env` file in the `backend/` directory:

```bash
# Copy example file
cd backend
cp .env.example .env
```

**Edit `.env` with your credentials:**

```env
# Database Configuration
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password_here

# JWT Configuration (generate with: openssl rand -base64 32)
JWT_SECRET=your_256bit_secret_here

# Email Configuration (Gmail SMTP)
EMAIL_USERNAME=your-email@gmail.com
EMAIL_APP_PASSWORD=your_gmail_app_password

# reCAPTCHA Configuration
RECAPTCHA_SECRET_KEY=your_recaptcha_secret_key
```

**application.properties** (uses environment variables):

```properties
# Server Configuration
server.port=8081

# PostgreSQL Database
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_APP_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# reCAPTCHA
recaptcha.secret=${RECAPTCHA_SECRET_KEY}
recaptcha.verify.url=https://www.google.com/recaptcha/api/siteverify

# File Upload
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB

# Flyway Database Migrations
spring.flyway.enabled=true
```

### Environment Variable Generation

**Generate JWT Secret:**

```bash
# Linux/Mac/Git Bash
openssl rand -base64 32

# Windows PowerShell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
[Convert]::ToBase64String($bytes)
```

**Get Gmail App Password:**

1. Go to https://myaccount.google.com/security
2. Enable 2-Step Verification
3. Go to App Passwords
4. Generate password for "Mail"
5. Use the 16-character password

**Get reCAPTCHA Keys:**

1. Go to https://www.google.com/recaptcha/admin
2. Register a new site (v2 Checkbox)
3. Add domain: `localhost` (for development)
4. Copy Secret Key for backend
5. Copy Site Key for frontend (`index.html`)

### Frontend Configuration

Update API URLs if backend is not on localhost:8081:

**Edit service files:**

```typescript
// frontend/src/app/features/auth/services/auth.ts
private baseUrl = 'https://your-backend-url.com/api';

// frontend/src/app/features/documents/services/documents.service.ts
private baseUrl = 'https://your-backend-url.com/api/documents';

// frontend/src/app/features/admin/services/admin.ts
private baseUrl = 'https://your-backend-url.com/api/admin';
```

**Update reCAPTCHA site key:**

```html
<!-- frontend/src/index.html -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
```

```typescript
// frontend/src/app/features/auth/components/signup/signup.ts
grecaptcha.render("recaptcha-container", {
  sitekey: "YOUR_SITE_KEY_HERE",
});
```

## 🤖 PDF Text Extraction & RAG Features

### Three Extraction Methods

The system implements a smart **fallback cascade** with three PDF text
extraction methods:

#### 1. **Apache Tika** (PRIMARY - Tried First)

- ✅ **License**: Apache 2.0 (completely free, commercial-friendly)
- ✅ **Performance**: Moderate (typically 200-800ms)
- ✅ **Page Boundaries**: Provides page-by-page extraction for header/footer
  removal
- ✅ **Robustness**: Best with edge cases, malformed PDFs
- ✅ **Universal**: Handles PDFs, Word, Excel, PowerPoint, and 1000+ formats
- ✅ **OCR Support**: Can integrate with Tesseract for scanned documents
- 🎯 **Why First**: Page boundaries enable `HeaderFooterStripper` to remove
  repeated headers/footers

#### 2. **Poppler pdftotext** (FALLBACK #1 - If Tika Poor Quality)

- ✅ **License**: GPL (requires consideration for commercial use)
- ✅ **Performance**: Very fast
- ✅ **Accuracy**: Excellent, industry-standard
- ✅ **Layout Preservation**: Uses `-layout` flag to preserve spatial layout
- ⚠️ **Deployment**: Requires external binary installation
- ⚠️ **Portability**: Platform-specific (Linux/Windows/Mac)
- 🎯 **Position**: Second attempt if Tika produces poor quality text

#### 3. **Apache PDFBox** (FALLBACK #2 - If pdftotext Poor Quality)

- ✅ **License**: Apache 2.0 (completely free)
- ✅ **Performance**: Fast (typically 100-500ms)
- ✅ **Maintenance**: Actively maintained, large community
- ✅ **Accuracy**: Good for 95% of standard PDFs
- ⚠️ **Limitations**: Struggles with complex layouts, scanned documents
- 🎯 **Position**: Third attempt if both Tika and pdftotext produce poor quality

#### 4. **Tesseract OCR** (FALLBACK #3 - If All Empty)

- ✅ **License**: Apache 2.0
- ✅ **Capability**: Handles scanned/image-only PDFs
- ⚠️ **Performance**: Slow (OCR is computationally expensive)
- ⚠️ **Requirement**: External Tesseract installation
- 🎯 **Position**: Only used when all text-layer extractors return empty
  (indicates scanned PDF)

### Smart Extraction Router

The system uses `ExtractionRouterService` to intelligently select the best
extraction method:

**Fallback Cascade Logic:**

1. **Try Tika First** ✅
   - Extract text page-by-page
   - Analyze quality (word count, printable ratio, fragmentation)
   - If good quality → Use Tika result with page boundaries

2. **If Poor Quality → Try pdftotext** (with `-layout` flag)
   - Extract text preserving spatial layout
   - Analyze quality
   - If good quality → Use pdftotext result

3. **If Still Poor → Try PDFBox**
   - Extract text using PDFTextStripper
   - Analyze quality
   - If good quality → Use PDFBox result

4. **If All Three Empty → Try OCR** (scanned/image PDF detected)
   - Check if Tesseract is available
   - If available → Run OCR extraction
   - If OCR finds text → Use OCR result
   - If OCR also empty → Mark as "none" (genuinely blank/unreadable)

5. **If Tesseract Unavailable → Mark NEEDS_OCR**
   - Flag document for manual OCR processing later

6. **If None Pass Quality Bar But Not All Empty → Pick by Word Count**
   - Compare word counts (not character counts to avoid pdftotext `-layout`
     whitespace)
   - Return extraction with highest word count

**Quality Checks:**

- ✅ Not empty (wordCount > 0)
- ✅ Not garbled (printableRatio indicates proper encoding)
- ✅ Not fragmented (no runs of very short lines)

**Why This Order?**

- **Tika First**: Provides page boundaries needed for header/footer removal
- **pdftotext Second**: Fast and accurate when Tika fails
- **PDFBox Third**: Pure Java fallback, no external dependencies
- **OCR Last**: Computationally expensive, only for scanned documents

Access the comparison tool at: `http://localhost:4200/extraction-comparison`

**Features:**

- Extract text from any uploaded document using all three methods
- Compare extraction times (milliseconds)
- Compare character counts
- View side-by-side text outputs
- Identify which method works best for your documents

### API Endpoint

```http
GET /api/documents/{id}/text/compare
```

**Response:**

```json
{
  "pdfBoxText": "Extracted text from PDFBox...",
  "tikaText": "Extracted text from Tika...",
  "pdftotextText": "Extracted text from pdftotext...",
  "pdfBoxTimeMs": 150,
  "tikaTimeMs": 320,
  "pdftotextTimeMs": 95,
  "pdfBoxLength": 5432,
  "tikaLength": 5450,
  "pdftotextLength": 5438,
  "pdfBoxDiagnostics": {
    "wordCount": 892,
    "printableRatio": 0.96,
    "fragmentedLines": false,
    "empty": false,
    "looksGarbled": false
  },
  "tikaDiagnostics": { "wordCount": 895, "printableRatio": 0.97 /* ... */ },
  "pdftotextDiagnostics": {
    "wordCount": 894,
    "printableRatio": 0.96 /* ... */
  },
  "notes": [
    "All three methods extracted similar amounts of text",
    "Tika provides page boundaries for header/footer removal"
  ]
}
```

### Actual Production Implementation

**The system uses smart routing with fallback cascade:**

```java
// ExtractionRouterService.extract() - Actual Implementation

// 1. Try Tika first (provides page boundaries)
List<String> tikaPages = tikaService.extractTextByPage(pdfData);
ExtractionDiagnostics tikaDiag = TextQualityAnalyzer.analyze(tikaPages);
if (isGood(tikaDiag)) {
    // Use Tika result with pages for HeaderFooterStripper
    return new RoutedExtraction("tika", joinPages(tikaPages), tikaPages);
}

// 2. Try pdftotext if Tika quality poor
String pdftotextText = pdftotextService.extractText(pdfData); // uses -layout flag
ExtractionDiagnostics pdftotextDiag = TextQualityAnalyzer.analyze(pdftotextText);
if (isGood(pdftotextDiag)) {
    return new RoutedExtraction("pdftotext", pdftotextText, null);
}

// 3. Try PDFBox if pdftotext quality poor
String pdfBoxText = pdfBoxService.extractText(pdfData);
ExtractionDiagnostics pdfBoxDiag = TextQualityAnalyzer.analyze(pdfBoxText);
if (isGood(pdfBoxDiag)) {
    return new RoutedExtraction("pdfbox", pdfBoxText, null);
}

// 4. If all three empty, try OCR for scanned PDFs
if (tikaDiag.empty() && pdftotextDiag.empty() && pdfBoxDiag.empty()) {
    if (ocrService.isAvailable()) {
        String ocrText = ocrService.extractText(pdfData);
        if (!isEmpty(ocrText)) {
            return new RoutedExtraction("ocr", ocrText, null);
        }
        return new RoutedExtraction("none", "", null); // Genuinely blank
    }
    return new RoutedExtraction("needs_ocr", "", null); // Flag for manual processing
}

// 5. None passed quality bar, pick by word count (not char count)
return pickByWordCount(tikaText, tikaDiag, pdftotextText, pdftotextDiag,
                        pdfBoxText, pdfBoxDiag);
```

**Benefits of this cascading approach:**

- ✅ **Page-by-page extraction** from Tika enables `HeaderFooterStripper`
- ✅ **Automatic fallback** to faster methods if Tika has issues
- ✅ **Quality analysis** ensures best extraction is used
- ✅ **OCR integration** for scanned documents (when Tesseract available)
- ✅ **Smart recovery** picks best available result if all have quality issues
- ✅ **Licensing flexibility** - Tika/PDFBox are Apache 2.0, pdftotext is
  optional
- ✅ **Cross-platform** (except pdftotext which needs binary)

### RAG Architecture (Future Implementation)

This extraction feature is the foundation for RAG (Retrieval-Augmented
Generation):

1. **Text Extraction** ✅ (Current implementation)
   - Extract text from uploaded PDFs
   - Choose best extraction method per document

2. **Text Chunking** 🚧 (Next step)
   - Split text into semantic chunks
   - Overlapping chunks for context preservation
   - Chunk size optimization (256-512 tokens)

3. **Vector Embeddings** 🚧 (Planned)
   - Generate embeddings using OpenAI, HuggingFace, or local models
   - Store embeddings in vector database (Pinecone, Weaviate, PostgreSQL
     pgvector)

4. **Semantic Search** 🚧 (Planned)
   - Query embeddings for similar content
   - Retrieve relevant chunks
   - Rank by similarity score

5. **LLM Integration** 🚧 (Planned)
   - Send retrieved context to LLM (GPT-4, Claude, Llama)
   - Generate answers based on document content
   - Cite sources with page numbers

See [PDF_EXTRACTION_COMPARISON.md](./backend/PDF_EXTRACTION_COMPARISON.md) for
detailed technical comparison.

---

## 🐛 Troubleshooting

### Backend Issues

**Problem:** Port 8080 already in use

```bash
# Windows: Find and kill process
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or change port in application.properties
server.port=8081
```

**Problem:** Database connection error

- Ensure PostgreSQL is installed and running
- Verify database name is `authdb`
- Check credentials in application.properties
- Create database if it doesn't exist: `CREATE DATABASE authdb;`

### Frontend Issues

**Problem:** CORS errors

- Ensure backend has CORS configured
- Check backend includes: `@CrossOrigin(origins = "http://localhost:4200")`

**Problem:** Port 4200 in use

```bash
# Kill process on port 4200 (Windows)
netstat -ano | findstr :4200
taskkill /PID <PID> /F

# Or specify different port
ng serve --port 4201
```

**Problem:** npm install fails

```bash
# Clear cache and retry
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

### Connection Issues

**Problem:** Frontend can't reach backend

- Verify backend is running: `http://localhost:8080/api/auth/login`
- Check browser console for errors
- Verify API URLs in frontend services
- Check firewall isn't blocking connections

## 📚 Documentation

Comprehensive documentation available in project directories:

### Getting Started

- **[QUICK_START.md](./QUICK_START.md)** - 5-minute setup guide for immediate
  start
- **[README.md](./README.md)** - This file, main project overview

### Security & Deployment

- **[SECURITY_STATUS_FINAL.md](./backend/SECURITY_STATUS_FINAL.md)** - Complete
  security assessment (9.5/10)
- **[PRODUCTION_DEPLOYMENT.md](./backend/PRODUCTION_DEPLOYMENT.md)** -
  Deployment guide for 7 platforms

### Backend Documentation

- **[backend/SETUP.md](./backend/SETUP.md)** - Backend setup instructions
- **[backend/HELP.md](./backend/HELP.md)** - Spring Boot reference guide
- **[backend/PDF_EXTRACTION_COMPARISON.md](./backend/PDF_EXTRACTION_COMPARISON.md)** -
  Technical comparison of PDF extraction methods

### Frontend Documentation

- **[frontend/README.md](./frontend/README.md)** - Complete Angular 22
  documentation with migration guide

### Deployment Scripts

- **[backend/deploy-to-server.sh](./backend/deploy-to-server.sh)** - Automated
  deployment script
- **[backend/setup-production.sh](./backend/setup-production.sh)** - Production
  environment setup script

### Configuration Files

- **[backend/.env.example](./backend/.env.example)** - Environment variables
  template
- **[backend/.env.production.example](./backend/.env.production.example)** -
  Production environment template

## ✨ Features

### ✅ Implemented

#### 🔐 Authentication & Security

- User registration with comprehensive validation
- Email verification with token-based activation
- Login/Logout with JWT tokens
- Password reset flow (forgot password → email → reset)
- Change password with current password verification
- Google reCAPTCHA v2 bot protection
- Role-based access control (USER/ADMIN)
- Environment variable protection for secrets
- 9.5/10 security score (production-ready)

#### 👥 User Management

- User profile management (view/edit)
- Admin panel for managing all users
- Role assignment (USER ↔ ADMIN)
- Department management
- User deletion with confirmation
- Inline editing capabilities

#### 📂 Document Management

- PDF document upload with validation
- Document download
- Advanced search and filtering:
  - By document name
  - By uploader
  - By department
  - By tags
  - Date range filtering (from/to)
- Tag-based organization
- Document metadata tracking
- Document versioning (upload new versions)
- Document sharing with visibility control:
  - PUBLIC - Everyone can access
  - DEPARTMENT - Department members only
  - SPECIFIC_USERS - Share with selected users
  - PRIVATE - Owner only
- Version history view

#### 🤖 PDF Text Extraction & RAG

- **Three extraction methods**:
  - **Apache PDFBox** - Fast, lightweight, Apache 2.0 license
  - **Apache Tika** - Universal parser, handles edge cases
  - **Poppler pdftotext** - External tool, high accuracy
- **Performance comparison dashboard**:
  - Extraction time (milliseconds)
  - Characters extracted
  - Side-by-side text comparison
- **Technical comparison document**
  ([PDF_EXTRACTION_COMPARISON.md](./backend/PDF_EXTRACTION_COMPARISON.md))
- Ready for RAG implementation (embeddings, vector search)

#### 🎨 User Interface

- Responsive design (mobile, tablet, desktop)
- Modern Angular 22 with Signals
- Custom SCSS design system
- Loading states and error handling
- Smooth animations and transitions
- Accessibility compliant (WCAG)

#### 🚀 Deployment & DevOps

- Complete deployment guides for 7 platforms
- Docker and Docker Compose support
- Systemd service configuration
- Automated deployment scripts
- Environment variable management
- Production security checklist

### 🚧 Potential Enhancements

- Two-factor authentication (2FA)
- Real-time notifications (WebSocket)
- Advanced search with Elasticsearch
- Document comments and annotations
- OCR for scanned PDFs
- Document preview in-browser
- Audit logs and activity tracking
- RAG chatbot for document Q&A
- Batch document processing
- API rate limiting
- Document expiration and archival

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📄 License

This project is for educational and demonstration purposes.

## 👨‍💻 Tech Stack Summary

### Frontend

- **Framework**: Angular 22 (latest with Signals)
- **Language**: TypeScript 6.0
- **State Management**: Angular Signals
- **Forms**: Reactive Forms with custom validators
- **HTTP**: HttpClient with JWT interceptors
- **Styling**: SCSS with custom design system
- **Build Tool**: Angular CLI with Vite
- **Testing**: Vitest
- **Security**: reCAPTCHA v2, XSS protection

### Backend

- **Framework**: Spring Boot 4.1
- **Language**: Java 21
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL 15 with JPA/Hibernate
- **Database Migrations**: Flyway
- **Email**: Spring Mail (SMTP/Gmail)
- **PDF Processing**: Apache PDFBox 3.0, Apache Tika 3.0
- **Build Tool**: Maven
- **Testing**: JUnit + Mockito

### Database

- **RDBMS**: PostgreSQL 15
- **ORM**: Hibernate (JPA)
- **Migrations**: Flyway
- **Tables**: users, documents, tags, document_shared_users

### Security

- **Authentication**: JWT (JSON Web Tokens)
- **Password Hashing**: BCrypt
- **Bot Protection**: Google reCAPTCHA v2
- **Environment Variables**: Externalized secrets (.env)
- **CORS**: Configured for cross-origin requests
- **Security Score**: 9.5/10 (production-ready)

### DevOps & Deployment

- **Version Control**: Git
- **CI/CD Ready**: GitHub Actions, GitLab CI compatible
- **Containerization**: Docker, Docker Compose
- **Deployment Platforms**: AWS, Azure, GCP, Heroku, DigitalOcean, Vercel,
  Netlify
- **Process Management**: systemd, PM2
- **Monitoring**: Spring Boot Actuator (optional)

---

## 🎯 Getting Started Checklist

- [ ] Install prerequisites: Node.js 18+, Java 21, Maven, PostgreSQL 15+
- [ ] Clone the repository
- [ ] Create PostgreSQL database: `createdb authdb`
- [ ] Configure backend `.env` file with secrets
- [ ] Generate JWT secret: `openssl rand -base64 32`
- [ ] Set up Gmail app password for email
- [ ] Get reCAPTCHA keys from Google
- [ ] Start backend server: `cd backend && ./mvnw spring-boot:run` (port 8081)
- [ ] Install frontend dependencies: `cd frontend && npm install`
- [ ] Start frontend server: `npm start` (port 4200)
- [ ] Access application at http://localhost:4200
- [ ] Create an account and test features
- [ ] Upload a PDF document
- [ ] Test PDF extraction comparison at `/extraction-comparison`
- [ ] Read security documentation:
      [SECURITY_STATUS_FINAL.md](./backend/SECURITY_STATUS_FINAL.md)
- [ ] Review deployment guide:
      [PRODUCTION_DEPLOYMENT.md](./backend/PRODUCTION_DEPLOYMENT.md)

## 🔒 Security Checklist (Production)

- [ ] Generate strong production secrets (JWT, database password)
- [ ] Never commit `.env` files to Git
- [ ] Set environment variables on production server
- [ ] Enable HTTPS/TLS
- [ ] Update CORS to production domain
- [ ] Set up database backups
- [ ] Configure logging and monitoring
- [ ] Reduce JWT expiration to 1 hour
- [ ] Add security headers (XSS, clickjacking protection)
- [ ] Set up rate limiting for auth endpoints
- [ ] Review and rotate secrets every 90 days
- [ ] Restrict SSH access and use key-based authentication

## 🌐 Access Points

Once everything is running:

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8081/api
- **Extraction Comparison**: http://localhost:4200/extraction-comparison
- **Backend Health**: http://localhost:8081/actuator/health (if Actuator
  enabled)
- **PostgreSQL**: localhost:5432 (authdb database)

---

## 📊 Project Status

| Component          | Status              | Version         | Score              |
| ------------------ | ------------------- | --------------- | ------------------ |
| **Frontend**       | ✅ Production Ready | Angular 22      | Fully migrated     |
| **Backend**        | ✅ Production Ready | Spring Boot 4.1 | Complete           |
| **Security**       | ✅ Excellent        | -               | 9.5/10             |
| **Documentation**  | ✅ Complete         | -               | Comprehensive      |
| **Deployment**     | ✅ Ready            | -               | 7 platforms        |
| **PDF Extraction** | ✅ Implemented      | 3 methods       | Performance tested |
| **Testing**        | ⚠️ Partial          | Vitest          | Needs expansion    |

---

**Built with ❤️ using Angular 22, Spring Boot 4.1, and PostgreSQL**

_Last Updated: January 2025_

# angular-spring-boot-rag

# angular-spring-boot-rag

angular-spring-boot-rag
