# 💰 Finance Intelligence Backend 🚀

<p align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Security-6.2-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/Database-H2_In--Memory-004E89?style=for-the-badge&logo=databricks&logoColor=white" />
</p>

---

## 🌟 Project Overview
A robust and secure financial data processing engine designed for real-time transaction tracking and user management. This backend implements **Role-Based Access Control (RBAC)** to ensure sensitive financial data is only modified by authorized administrators.

### ✨ Key Technical Highlights
* 🛡️ **Advanced Security**: Custom `SecurityFilterChain` protecting RESTful endpoints.
* 🔐 **RBAC Implementation**: Distinct permissions for `ROLE_ADMIN` and `ROLE_USER`.
* 📖 **OpenAPI 3.0**: Fully interactive **Swagger UI** for API testing and documentation.
* ⚡ **H2 Persistence**: High-speed in-memory database for seamless evaluation.

---

## 🛠️ Tech Stack & Architecture

| Layer | Technology | Role |
| :--- | :--- | :--- |
| **Framework** | Spring Boot 3.x | Core Application Logic |
| **Security** | Spring Security 6 | Authentication & Authorization |
| **Database** | H2 Database | In-Memory Data Persistence |
| **Documentation**| Swagger/OpenAPI | Interactive API Explorer |
| **Build Tool** | Maven | Dependency & Lifecycle Management |

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone [https://github.com/Dishagoel1804/finance-backend.git](https://github.com/Dishagoel1804/finance-backend.git)
cd finance-backend
```
### 2. Run the Application
```bash
You can run the app directly from IntelliJ or use the terminal:

mvn spring-boot:run
```

### 3. Access the Dashboard
```bash
Once started, explore the API via the Swagger UI:
🔗 http://localhost:8080/swagger-ui/index.html
```

🔐 Testing the Security Logic
To verify that the Authentication and PATCH endpoints are working correctly:

User Registration: Use the POST /api/users/signup endpoint to create a user. Ensure you set "role": "ADMIN".

Authentication: Click the Authorize 🔓 button at the top of the Swagger page. Enter the username and password you just created.

Protected Action: Navigate to the PATCH endpoint for users. Enter a valid User ID (e.g., 1) and execute.

Validation: A 200 OK response confirms successful RBAC authorization. (Attempting this without the Authorize step will return 401 Unauthorized).

🤖 AI Collaboration Log
During development, AI-assisted tools were utilized to solve complex architectural challenges:

Security Configuration: Resolved circular dependency loops and session management filters.

Environment Setup: Debugged Maven SDK mismatches and dependency sync errors.

JPA Optimization: Configured H2 persistence layers for consistent data state during testing.
