# BC Student Wellness Management System

## 📘 Overview

This is a Java-based application developed as part of the **PRG3781 Project (2025)** for **Belgium Campus**. The system assists with student wellness management by providing:

- A **web-based** login and registration portal.
- A **desktop** application for managing appointments, counselors, and student feedback.

> 🔹 Built with Core Java, JSP/Servlets, Swing, JDBC  
> 🔹 Databases: PostgreSQL (Web App) & JavaDB (Desktop App)

---

## 🛠️ Tech Stack

| Layer         | Technology         |
|---------------|--------------------|
| Backend       | Java SE, Servlets  |
| Frontend      | JSP (Web), Swing (Desktop GUI) |
| Web Server    | Apache Tomcat 9+   |
| Databases     | PostgreSQL, JavaDB (Derby) |
| Tools         | Git, GitHub, JDBC, NetBeans/IntelliJ |

---

## 📁 Project Structure

```bash
Wellness-Management-System/
├── desktop-app/
│   ├── src/
│   │   ├── lib/                          # Derby JARs for JavaDB
│   │   └── LibrarySystem/
│   │       ├── controller/              # MVC Controllers
│   │       ├── model/                   # Data Models (Appointment, Counselor, Feedback, etc.)
│   │       ├── view/                    # GUI Panels (AppointmentPanel, FeedbackPanel, etc.)
│   │       └── utils/                   # DB setup & connection utilities
│   │           ├── DBConnection.java
│   │           ├── DatabaseSetup.java
│   │           └── TestConnection.java
│   ├── desktop-app.iml                  # IntelliJ module config
│
├── web-app/
│   ├── src/
│   │   ├── servlets/                    # Java Servlets (Login, Register)
│   │   │   ├── LoginServlet.java
│   │   │   └── RegisterServlet.java
│   │   └── utils/                       # Utility classes (e.g., AuthUtils)
│   │       └── AuthUtils.java
│   ├── web/                             # Web content root
│   │   ├── index.jsp                    # Landing page
│   │   ├── login.jsp                    # Login page
│   │   ├── register.jsp                 # Registration page
│   │   ├── dashboard.jsp                # User dashboard (post-login)
│   │   └── WEB-INF/
│   │       └── web.xml                  # Deployment descriptor for Tomcat
│   ├── lib/                             # Web app libraries (if any)
│   ├── out/                             # Compiled files (ignored in Git)
│   ├── .idea/                           # IntelliJ config (auto-generated)
│   ├── web-app.iml                      # IntelliJ module file
│
├── sql/
│   └── schema_postgresql.sql            # PostgreSQL schema (users table, etc.)
│
├── Student_WellnessDB/                 # JavaDB database (can be ignored in Git)
├── .gitignore
├── README.md
└── LICENSE


```
---

## ✅ Features

### 🔐 Web Application (Milestone 1)

- User Registration & Login (JSP + Servlets)
- Validation for email, phone, and password
- PostgreSQL database integration
- Session tracking & redirection
- Personalized dashboard view

### 🖥️ Desktop Application (Milestone 2)

- GUI built with Java Swing
- Appointment Management (Book, View, Update, Cancel)
- Counselor Management (CRUD)
- Feedback System (Rating, Comment, Edit/Delete)
- JavaDB integration for local persistence
- MVC (Model-View-Controller) architecture
- Input validation and error handling

---

## 🧪 Setup Instructions

### 🔧 Prerequisites

- Java JDK 17+
- Apache Tomcat 9+
- PostgreSQL Server (for web app)
- JavaDB / Apache Derby (for desktop app)
- Git

---

### 💡 Web Application Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-group-name/BC-Wellness-Management.git
2. Import the web-app/ folder into your IDE (e.g., NetBeans).
3. Setup PostgreSQL:
- Run the script in sql/schema_postgresql.sql to create the users table.
4. Deploy on Tomcat.
- Start Tomcat and open http://localhost:8080/web-app/index.jsp.
