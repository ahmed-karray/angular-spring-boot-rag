# 🚀 Quick Start Guide

Get your Document Management System running in 5 minutes!

## Prerequisites

- [x] Node.js 18+ installed
- [x] Java 21 installed
- [x] PostgreSQL 15+ installed
- [x] Git installed

## 1️⃣ Clone & Setup Database

```bash
# Clone repository
git clone <repository-url>
cd angular-spring-boot-rag

# Create PostgreSQL database
createdb authdb
```

## 2️⃣ Configure Backend

```bash
cd backend

# Copy environment template
cp .env.example .env

# Edit .env with your credentials
# (Use your favorite editor)
```

**Required values in `.env`:**
```env
DB_USERNAME=postgres
DB_PASSWORD=your_password
JWT_SECRET=generate_with_openssl
EMAIL_USERNAME=your_email@gmail.com
EMAIL_APP_PASSWORD=gmail_app_password
RECAPTCHA_SECRET_KEY=recaptcha_key
```

**Generate JWT Secret:**
```bash
# Linux/Mac/Git Bash
openssl rand -base64 32

# Windows PowerShell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
[Convert]::ToBase64String($bytes)
```

## 3️⃣ Start Backend

```bash
# Still in backend/
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

✅ Backend should start on **http://localhost:8081**

## 4️⃣ Start Frontend

```bash
# Open new terminal
cd frontend

# Install dependencies
npm install

# Start dev server
npm start
```

✅ Frontend should start on **http://localhost:4200**

## 5️⃣ Access Application

Open browser: **http://localhost:4200**

1. Click "Sign Up"
2. Fill registration form
3. Complete reCAPTCHA
4. Login and explore!

---

## 🎯 What to Try

### Basic Features
1. **Upload a PDF document**
   - Go to "Documents" page
   - Click "Upload Document"
   - Select a PDF file
   - Add tags (optional)
   - Click "Upload"

2. **Search & Filter**
   - Use search box to find documents by name
   - Filter by date range
   - Filter by department
   - Filter by tags

3. **Edit Your Profile**
   - Click on your name (top right)
   - Update your information
   - Change password

### Advanced Features

4. **PDF Text Extraction Comparison** (RAG Feature)
   - Navigate to: http://localhost:4200/extraction-comparison
   - Enter a document ID
   - Click "Compare"
   - View side-by-side comparison of 3 extraction methods
   - See performance metrics

5. **Document Versioning**
   - Upload a document
   - Click "Upload New Version"
   - Select updated file
   - View version history

6. **Document Sharing**
   - Upload a document
   - Click "Share" button
   - Choose visibility:
     - PUBLIC - Everyone can access
     - DEPARTMENT - Department members only
     - SPECIFIC_USERS - Select users
     - PRIVATE - Owner only

### Admin Features (ADMIN role only)

7. **User Management**
   - Go to "Admin Panel"
   - View all users
   - Edit user roles
   - Change user details
   - Delete users

---

## 🐛 Troubleshooting

### Backend won't start

**Issue:** Port 8081 already in use
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8081 | xargs kill -9
```

**Issue:** Database connection error
```bash
# Check PostgreSQL is running
# Windows: services.msc → PostgreSQL
# Linux: sudo systemctl status postgresql
# Mac: brew services list

# Verify database exists
psql -U postgres -l | grep authdb
```

**Issue:** Environment variables not loading
```bash
# Make sure .env file exists in backend/
ls -la backend/.env

# Check file format (no BOM, Unix line endings)
```

### Frontend won't start

**Issue:** Port 4200 in use
```bash
# Use different port
ng serve --port 4201
```

**Issue:** npm install fails
```bash
# Clear cache
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

### Can't login

**Issue:** CORS errors in browser console
- Make sure backend is running on port 8081
- Check `SecurityConfig.java` has correct CORS configuration
- Clear browser cache

**Issue:** reCAPTCHA not loading
- Check internet connection
- Verify reCAPTCHA script in `index.html`
- Check browser console for errors

---

## 📚 Next Steps

1. **Read Full Documentation**
   - [README.md](./README.md) - Complete project overview
   - [backend/SETUP.md](./backend/SETUP.md) - Backend details
   - [frontend/README.md](./frontend/README.md) - Frontend details

2. **Review Security**
   - [SECURITY_STATUS_FINAL.md](./backend/SECURITY_STATUS_FINAL.md)
   - Production security checklist

3. **Prepare for Deployment**
   - [PRODUCTION_DEPLOYMENT.md](./backend/PRODUCTION_DEPLOYMENT.md)
   - 7 platform deployment guides

4. **Explore PDF Extraction**
   - [PDF_EXTRACTION_COMPARISON.md](./backend/PDF_EXTRACTION_COMPARISON.md)
   - Technical comparison of extraction methods

---

## 🆘 Need Help?

- Check [README.md](./README.md) for detailed instructions
- Review troubleshooting section
- Check application logs
- Verify all prerequisites are installed
- Ensure environment variables are set correctly

---

**Happy coding! 🎉**
