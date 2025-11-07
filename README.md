# Gym Backend - Development Setup Guide

## 📋 Project Overview

This is the backend API for the Gym Management System built with:

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: MySQL 8.0 (Docker)
- **Build Tool**: Maven
- **Security**: Spring Security

## 🛠️ Prerequisites

Before you begin, ensure you have the following installed:

### Required Software
- **Java 17** (JDK)
- **Docker** and **Docker Compose**
- **Git**
- **Maven** (or use included Maven wrapper)

### Installation Instructions

#### Java 17
```bash
# Check if Java 17 is installed
java -version

# If not installed, download from:
# https://www.oracle.com/java/technologies/downloads/
# Or use package managers:

# macOS (Homebrew)
brew install openjdk@17

# Ubuntu/Debian
sudo apt install openjdk-17-jdk

# Windows
# Download and install from Oracle website
```

#### Docker
```bash
# macOS
brew install docker docker-compose

# Ubuntu/Debian
sudo apt install docker.io docker-compose

# Windows
# Download Docker Desktop from docker.com
```

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone [your-repo-url]
cd gym-backend
```

### 2. Start the Database
```bash
# Start MySQL container in background
docker-compose up -d

# Verify container is running
docker ps
```

### 3. Run the Application
```bash
# Using Maven wrapper (recommended)
./mvnw spring-boot:run

# Or if you have Maven installed globally
mvn spring-boot:run
```

### 4. Verify Setup
- Application will start on: **http://localhost:8080**
- Database will be available on: **localhost:3306**
- Check application logs for "Started ServingWebContentApplication"

## 🗄️ Database Configuration

### Database Details
- **Host**: localhost
- **Port**: 3306
- **Database Name**: gymdb
- **Username**: gymuser
- **Password**: gympassword
- **Root Password**: rootpassword

### Docker Commands
```bash
# Start database
docker-compose up -d

# Stop database
docker-compose down

# View database logs
docker-compose logs mysql

# Access MySQL CLI
docker exec -it gym-mysql mysql -u gymuser -p gymdb
```

### Database Management
- Tables are auto-created on first run (Hibernate DDL)
- Data persists in Docker volume `gym-backend_mysql_data`
- To reset database: `docker-compose down -v` then `docker-compose up -d`

## ⚙️ Configuration Files

### Application Properties
Located at: `src/main/resources/application.properties`

Key configurations:
```properties
# Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/gymdb
spring.datasource.username=gymuser
spring.datasource.password=gympassword

# Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
```

### Docker Compose
Located at: `docker-compose.yml`

Configures MySQL 8.0 container with persistent storage.

## 🔧 Development Workflow

### Starting Development
1. Pull latest changes: `git pull origin main`
2. Start database: `docker-compose up -d`
3. Run application: `./mvnw spring-boot:run`

### Making Changes
1. Create ticket branch: `git checkout -b WEB-123` (or your ticket number)
2. Make your changes
3. Test locally
4. Commit and push: `git add . && git commit -m "Your message"`
5. Create pull request

### Testing
```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

### Building for Production
```bash
# Create WAR file
./mvnw clean package

# WAR file will be in target/gym-backend-0.0.1-SNAPSHOT.war
```

## 🏗️ Project Structure

```
gym-backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/serving_web_content/
│   │   │   ├── controller/     # REST Controllers
│   │   │   ├── model/         # JPA Entities
│   │   │   ├── repository/    # Data Repositories
│   │   │   └── ...
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/        # Static web content
│   └── test/                  # Unit tests
├── docker-compose.yml         # Database configuration
├── pom.xml                   # Maven dependencies
└── SETUP.md                  # This file
```

## 🐛 Troubleshooting

### Common Issues

#### "Communications link failure"
**Problem**: Cannot connect to database
**Solution**:
1. Ensure Docker is running: `docker ps`
2. Start database: `docker-compose up -d`
3. Check container logs: `docker-compose logs mysql`

#### Port 3306 already in use
**Problem**: Another MySQL instance is running
**Solution**:
1. Stop other MySQL services
2. Or change port in `docker-compose.yml` (e.g., "3307:3306")
3. Update `application.properties` accordingly

#### "mvnw: Permission denied"
**Problem**: Maven wrapper not executable
**Solution**: `chmod +x mvnw`

#### Application won't start
**Solutions**:
1. Check Java version: `java -version` (should be 17+)
2. Clean and rebuild: `./mvnw clean compile`
3. Check application logs for specific errors

### Logs and Debugging
```bash
# Application logs are shown in console when running
# For debugging, set in application.properties:
logging.level.com.example=DEBUG
logging.level.org.springframework.web=DEBUG
```

## 🔐 Security Notes

### Development Security
- Spring Security is enabled with auto-generated password (shown in logs)
- Username: `user`
- Password: Check application startup logs for generated password

### Production Checklist
- [ ] Change all default passwords
- [ ] Use environment variables for sensitive data
- [ ] Enable HTTPS
- [ ] Configure proper CORS settings
- [ ] Review security configurations

## 🤝 Team Collaboration

### Environment Variables
Create `.env` file for local overrides (add to `.gitignore`):
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=gymdb
DB_USER=gymuser
DB_PASS=gympassword
```

### Git Workflow
- `main` branch for production-ready code
- Branch names should use ticket names and numbers (e.g., WEB-4, API-123)

### Code Standards
- Follow Java naming conventions
- Use meaningful commit messages
- Write unit tests for new features
- Document public APIs

## 📞 Support

### Getting Help
1. Check this documentation first
2. Search existing issues in the repository
3. Ask team members in Slack/Teams
4. Create new issue with detailed description

### Useful Commands Reference
```bash
# Maven
./mvnw clean compile          # Clean and compile
./mvnw spring-boot:run       # Run application
./mvnw test                  # Run tests
./mvnw package              # Build WAR file

# Docker
docker-compose up -d         # Start services
docker-compose down         # Stop services
docker-compose logs mysql   # View database logs
docker ps                   # List running containers

# Git
git status                  # Check status
git pull origin main       # Pull latest changes
git checkout -b feature/name # Create feature branch
```

---

**Happy Coding! 🚀**

For questions or issues, please contact the development team.