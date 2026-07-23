# 📄 Document Management System - Full Stack Application

> A modern full-stack document management system built with **Angular 22** (frontend) and **Spring Boot** (backend)

[![Angular](https://img.shields.io/badge/Angular-22-red?logo=angular)](https://angular.dev)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=spring)](https://spring.io)
[![TypeScript](https://img.shields.io/badge/TypeScript-6.0-blue?logo=typescript)](https://www.typescriptlang.org/)
[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)](https://www.java.com)

## 🌟 Project Overview

A production-ready document management system featuring:

- **Secure Authentication** - JWT-based auth with email verification and password reset
- **Role-Based Access Control** - USER and ADMIN roles with protected routes
- **Document Management** - Upload, download, search, and organize documents
- **User Management** - Admin panel for managing users and roles
- **Modern Architecture** - Angular 22 with Signals + Spring Boot REST API

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

### Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** v18 or higher ([Download](https://nodejs.org/))
- **npm** v11 or higher (comes with Node.js)
- **Java JDK** 17 or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven** 3.6+ (or use included Maven wrapper `mvnw`)
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

# Install dependencies and build (Windows)
.\mvnw.cmd clean install

# Or on Linux/Mac
./mvnw clean install

# Configure database (optional)
# Edit src/main/resources/application.properties if needed

# Run the backend server
.\mvnw.cmd spring-boot:run
# Or: ./mvnw spring-boot:run (Linux/Mac)
```

✅ **Backend should now be running on:** `http://localhost:8080`

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

- **IntelliJ IDEA**: Open both backend and frontend, run configurations separately
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
├── backend/                    # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/backend/
│   │   │   │   ├── auth/          # Authentication controllers & services
│   │   │   │   ├── user/          # User management
│   │   │   │   ├── document/      # Document management
│   │   │   │   ├── security/      # JWT & security config
│   │   │   │   ├── config/        # Application configuration
│   │   │   │   └── email/         # Email service
│   │   │   └── resources/
│   │   │       ├── application.properties  # Backend configuration
│   │   │       └── static/                # Uploaded files (documents)
│   │   └── test/                  # Backend tests
│   ├── pom.xml                    # Maven dependencies
│   └── mvnw.cmd                   # Maven wrapper (Windows)
│
├── frontend/                   # Angular 22 Application
│   ├── src/
│   │   ├── app/
│   │   │   ├── auth/          # Authentication components
│   │   │   ├── admin/         # Admin panel
│   │   │   ├── documents/     # Document management UI
│   │   │   ├── profile/       # User profile
│   │   │   ├── home/          # Dashboard
│   │   │   └── app.routes.ts # Route configuration
│   │   ├── styles/            # Global styles (SCSS)
│   │   └── main.ts            # Application entry point
│   ├── package.json           # npm dependencies
│   └── angular.json           # Angular CLI configuration
│
└── README.md                  # This file
```

## 🔗 API Endpoints

### Base URL: `http://localhost:8080/api`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| **POST** | `/auth/signup` | Register new user | No |
| **POST** | `/auth/login` | User login | No |
| **GET** | `/auth/me` | Get current user | Yes |
| **GET** | `/auth/verify-email` | Verify email | No |
| **POST** | `/auth/forgot-password` | Request password reset | No |
| **POST** | `/auth/reset-password` | Reset password | No |
| **PUT** | `/auth/profile` | Update profile | Yes |
| **GET** | `/admin/users` | Get all users | Admin only |
| **PUT** | `/admin/users/:id` | Update user | Admin only |
| **DELETE** | `/admin/users/:id` | Delete user | Admin only |
| **GET** | `/documents` | List documents | Yes |
| **POST** | `/documents` | Upload document | Yes |
| **GET** | `/documents/:id` | Get document | Yes |
| **DELETE** | `/documents/:id` | Delete document | Yes |
| **GET** | `/documents/:id/download` | Download file | Yes |

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

### Frontend Deployment

**Option 1: Vercel** (Recommended)
```bash
cd frontend
npm install -g vercel
vercel
```

**Option 2: Netlify**
```bash
# Build first
npm run build

# Deploy dist/frontend/browser/
```

**Option 3: GitHub Pages**
```bash
npm install -g angular-cli-ghpages
ng build --base-href /<repo-name>/
npx angular-cli-ghpages --dir=dist/frontend/browser
```

### Backend Deployment

**Option 1: Render / Railway** (Easiest)
- Connect GitHub repository
- Auto-detect Spring Boot
- Deploy with one click

**Option 2: Heroku**
```bash
heroku create
git push heroku main
```

**Option 3: Docker**
```dockerfile
# Dockerfile (in backend/)
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
docker build -t doc-management-backend .
docker run -p 8080:8080 doc-management-backend
```

**Option 4: AWS / Azure / GCP**
- Package as JAR
- Deploy to EC2, App Service, or Compute Engine
- Configure environment variables

### Full Stack Deployment

**Option 1: Separate Hosting**
- Frontend: Vercel/Netlify
- Backend: Render/Railway/Heroku
- Update frontend API URL to point to backend

**Option 2: Same Server**
- Build frontend
- Copy `dist/frontend/browser` to `backend/src/main/resources/static`
- Deploy backend JAR (serves frontend automatically)

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# Server Port
server.port=8081

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=postgres
spring.datasource.password=your_password

# JWT Secret (change this!)
jwt.secret=your-secret-key-change-this-in-production

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# File Upload
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
```

### Frontend Configuration

Update API URLs in services if backend is not on localhost:8080:

```typescript
// frontend/src/app/auth/auth.ts
private readonly API_URL = 'https://your-backend-url.com/api';
```

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

Detailed documentation available in each directory:

- **Frontend**: See [frontend/README.md](./frontend/README.md) for complete Angular documentation
- **Backend**: See [backend/HELP.md](./backend/HELP.md) for Spring Boot reference

## ✨ Features

### ✅ Implemented
- User registration with validation
- Email verification
- Login/Logout with JWT
- Password reset flow
- User profile management
- Admin user management panel
- Document upload/download
- Document search and filtering
- Role-based access control (USER/ADMIN)
- Responsive design
- reCAPTCHA integration

### 🚧 Potential Enhancements
- Two-factor authentication (2FA)
- Document versioning
- Document sharing between users
- Real-time notifications
- File preview (PDF, images)
- Audit logs
- Advanced search with Elasticsearch
- Document comments and annotations

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
- **Framework**: Angular 22
- **Language**: TypeScript 6.0
- **State Management**: Angular Signals
- **Forms**: Reactive Forms
- **HTTP**: HttpClient with Interceptors
- **Styling**: SCSS
- **Build Tool**: Angular CLI
- **Testing**: Vitest

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL with JPA/Hibernate
- **Build Tool**: Maven
- **Testing**: JUnit + Mockito
- **Email**: Spring Mail

---

## 🎯 Getting Started Checklist

- [ ] Install Node.js (v18+) and Java (17+)
- [ ] Clone the repository
- [ ] Configure backend database (if needed)
- [ ] Start backend server (port 8080)
- [ ] Install frontend dependencies
- [ ] Start frontend server (port 4200)
- [ ] Access application at http://localhost:4200
- [ ] Create an account and test features
- [ ] Read detailed docs in frontend/README.md

## 🌐 Access Points

Once everything is running:

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080/api
- **Backend Health**: http://localhost:8080/actuator/health (if enabled)

---

**Built with ❤️ using Angular 22 and Spring Boot**

*Last Updated: 2026*
# angular-spring-boot-rag
# angular-spring-boot-rag
angular-spring-boot-rag
