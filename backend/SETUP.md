# Backend Setup Guide

## Prerequisites

1. **Java 17+** installed
2. **PostgreSQL** installed and running
3. **Maven** (or use included `mvnw`)

## Database Setup

1. Install PostgreSQL if not already installed
2. Create a database:
   ```sql
   CREATE DATABASE authdb;
   ```

## Configuration

1. **Copy the example configuration file:**
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

2. **Edit `application.properties` with your actual values:**
   ```properties
   # Database credentials
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   
   # JWT Secret (generate a random 256-bit key)
   jwt.secret=your-long-random-secret-key-here
   
   # Email Configuration
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-gmail-app-password
   
   # reCAPTCHA Keys (get from https://www.google.com/recaptcha)
   recaptcha.secret-key=your-recaptcha-secret-key
   ```

3. **Generate a JWT Secret Key (optional):**
   ```bash
   # Using openssl
   openssl rand -base64 32
   
   # Or online: https://generate-random.org/api-key-generator
   ```

4. **Get Gmail App Password:**
   - Go to Google Account Settings
   - Enable 2-Factor Authentication
   - Generate App Password for "Mail"
   - Use that password (not your regular password)

5. **Get reCAPTCHA Keys:**
   - Visit: https://www.google.com/recaptcha/admin
   - Register your site
   - Get Site Key (for frontend) and Secret Key (for backend)

## Running the Application

```bash
# Using Maven wrapper (recommended)
./mvnw spring-boot:run

# Or if Maven is installed
mvn spring-boot:run
```

The backend will start on: **http://localhost:8081**

## Important Security Notes

⚠️ **Never commit `application.properties` with real credentials!**

- The actual `application.properties` file is in `.gitignore`
- Only the `application.properties.example` template is tracked by Git
- Each developer should create their own local `application.properties` file

## Troubleshooting

### Database Connection Error
- Ensure PostgreSQL is running
- Check database name is `authdb`
- Verify username and password are correct
- Create the database if it doesn't exist

### Port Already in Use
- Change `server.port` in `application.properties`
- Or stop the process using port 8081

### Email Not Sending
- Verify Gmail App Password (not regular password)
- Enable "Less Secure App Access" if needed
- Check SMTP settings are correct
