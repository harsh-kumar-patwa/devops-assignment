# DevOps CI/CD Pipeline Project

A production-grade CI/CD pipeline using GitHub Actions for a Java Spring Boot application.

## Project Overview

This project demonstrates:
- **CI Pipeline**: Build, test, security scans, Docker containerization
- **CD Pipeline**: Kubernetes deployment via self-hosted runner

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      CI Pipeline                             │
├─────────────────────────────────────────────────────────────┤
│ Checkout → Java Setup → Lint → Tests → Build → Docker Build │
│                         ↓                                    │
│              SAST (CodeQL) + SCA (Dependency Check)         │
│                         ↓                                    │
│              Trivy Scan → Smoke Test → Push DockerHub       │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│                      CD Pipeline                             │
├─────────────────────────────────────────────────────────────┤
│         Pull Image → Deploy to K8s → Verify Rollout         │
└─────────────────────────────────────────────────────────────┘
```

## CI Pipeline Stages

| Stage | Tool | Purpose |
|-------|------|---------|
| Checkout | actions/checkout | Get source code |
| Setup Java | actions/setup-java | Install JDK 17 |
| Linting | Checkstyle | Code standards |
| SAST | CodeQL | Security vulnerabilities |
| SCA | OWASP Dependency Check | Vulnerable libraries |
| Unit Tests | JUnit + Maven | Business logic validation |
| Build | Maven | Create JAR |
| Docker Build | docker build | Containerize |
| Image Scan | Trivy | Container CVEs |
| Smoke Test | curl | Verify container works |
| Push | docker push | Publish to DockerHub |

## CD Pipeline

- Triggers after CI success
- Deploys to K3s cluster via self-hosted runner
- Verifies deployment rollout

## Setup Instructions

### 1. GitHub Secrets

Add these in repo Settings → Secrets → Actions:
- `DOCKERHUB_USERNAME`: Your DockerHub username
- `DOCKERHUB_TOKEN`: DockerHub access token

### 2. EC2 Setup (for CD)

1. Launch Ubuntu 22.04 EC2 (t2.medium)
2. Security group: ports 22, 80, 443, 30000-32767
3. SSH and run:
   ```bash
   curl -O https://raw.githubusercontent.com/harsh-kumar-patwa/devops-assignment/main/scripts/setup-ec2.sh
   chmod +x setup-ec2.sh
   ./setup-ec2.sh
   ```
4. Set up GitHub self-hosted runner (repo Settings → Actions → Runners)

### 3. Access Application

After CD runs: `http://EC2_PUBLIC_IP:30080/api/health`

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/health` | GET | Health check |
| `/api/greeting` | GET | Greeting message |
| `/api/version` | GET | App version |

## Run Locally

```bash
# Build and run
mvn clean package
java -jar target/devops-demo-1.0.0.jar

# Or with Docker
docker build -t devops-demo .
docker run -p 8080:8080 devops-demo
```

## Why Each CI Stage?

| Stage | Why It Matters |
|-------|----------------|
| Linting | Prevents technical debt |
| Unit Tests | Prevents regressions |
| CodeQL | Detects OWASP Top 10 issues |
| Dependency Check | Supply chain security |
| Trivy | Prevents vulnerable images |
| Smoke Test | Ensures container works |

## Author

Harsh Kumar - DevOps Assignment
