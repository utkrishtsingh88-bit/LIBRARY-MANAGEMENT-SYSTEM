# 📚 Console-Based Library Management System

## 📖 Overview

This is a pure Java (JDK 11+) console-based **Library Management System** designed using Object-Oriented Programming principles.

The system allows librarians (admins) to manage books and members, issue and return books, search for books, view member profiles, and calculate fines for late returns. It does not require an external database; library data is persisted locally using Java file serialization.

## ✨ Features

- 📚 **Book Management**
  - Add books
  - Search books by title or author
  - List all books
  - Track total and available copies

- 👤 **Member Management**
  - Register new members
  - View member profiles
  - View borrowing history
  - View pending fines

- 🔄 **Issue & Return**
  - Issue books to registered members
  - Specify a return period
  - Return books
  - Automatically calculate late fines

- 💰 **Fine Management**
  - Calculates fines for overdue books
  - Members with unpaid fines cannot borrow new books
  - Members can pay pending fines

- 🔐 **Admin Security**
  - Admin authentication is required for restricted actions
  - Default Admin password: `admin123`

- 💾 **Data Persistence**
  - Library data is automatically saved to `library_data.ser`
  - Saved data is loaded automatically when the application starts

## 🛠️ Technology Stack

- ☕ **Language:** Java
- 📦 **JDK:** 11 or higher
- 🧩 **Programming Approach:** Object-Oriented Programming (OOP)
- 💾 **Data Storage:** Java File Serialization
- 🗄️ **External Database:** Not required
- 🔧 **External Runtime Dependencies:** None

## ⚙️ Prerequisites & Environment Setup

Before running the project, make sure the following are installed:

### ☕ Java Development Kit

Install **JDK 11 or higher** and make sure Java is added to your system PATH.

Verify the installation:

```bash
java -version
javac -version
```

Both commands should display JDK 11 or a newer version.

### 💻 Operating System

The application is a console-based Java application and can be run from a terminal/command prompt on Windows, Linux, or macOS.

## 📦 Dependencies

### Main Application

No external dependencies are required to compile or run the main application. It uses only the Java Standard Library.

### Testing Dependencies

The project includes JUnit 4 test files. Running the tests requires:

- `junit-4.13.2.jar`
- `hamcrest-core-1.3.jar`

Place these JAR files inside a `lib` directory at the project root:

```text
ppp/
├── lib/
│   ├── junit-4.13.2.jar
│   └── hamcrest-core-1.3.jar
├── src/
├── test/
├── screenshots/
├── README.md
└── statement.md
```

The main application itself does not require these JAR files.

## ⚙️ Configuration

No external database, API key, environment variable, or configuration file is required to run the main application.

### 🔐 Admin Configuration

The default Admin password is:

```text
admin123
```

### 💾 Data Storage

The application stores its library state in:

```text
library_data.ser
```

This file is generated/updated automatically when the library state changes and is loaded when the application starts.

## 🚀 Installation & Running

### 1️⃣ Clone the Repository

Clone the repository using Git:

```bash
git clone https://github.com/utkrishtsingh88-bit/LIBRARY-MANAGEMENT-SYSTEM.git
```

Then move into the project directory:

```bash
cd LIBRARY-MANAGEMENT-SYSTEM
```

### 2️⃣ Navigate to the Source Directory

```bash
cd src
```

### 3️⃣ Compile the Application

Compile all Java source files:

```bash
javac model/*.java service/*.java exception/*.java util/*.java Main.java
```

### 4️⃣ Run the Application

```bash
java Main
```

The Library Management System will start in the terminal and display the main menu.

## 🧪 Testing Instructions

JUnit 4 test files are provided in the `test/service/` directory.

Make sure the following files are available in the project's `lib` folder:

```text
junit-4.13.2.jar
hamcrest-core-1.3.jar
```

### Windows

From the `src` directory, compile the tests:

```bash
javac -cp ".;../lib/junit-4.13.2.jar;../lib/hamcrest-core-1.3.jar" ../test/service/*.java service/*.java model/*.java exception/*.java util/*.java
```

Run the tests:

```bash
java -cp ".;../lib/junit-4.13.2.jar;../lib/hamcrest-core-1.3.jar;../test" org.junit.runner.JUnitCore service.FineCalculatorTest service.LibraryServiceTest
```

### Linux/macOS

Replace the semicolons `;` in the classpath with colons `:`.

Compile:

```bash
javac -cp ".:../lib/junit-4.13.2.jar:../lib/hamcrest-core-1.3.jar" ../test/service/*.java service/*.java model/*.java exception/*.java util/*.java
```

Run:

```bash
java -cp ".:../lib/junit-4.13.2.jar:../lib/hamcrest-core-1.3.jar:../test" org.junit.runner.JUnitCore service.FineCalculatorTest service.LibraryServiceTest
```

## 📸 Screenshots

### 🏠 Main Menu



![Main Menu](screenshots/main_menu.png)



### 🔍 Search Results



![Search Results](screenshots/search_results.png)



### 📕 Issue Book



![Issue Book](screenshots/issue_book_result.png)



## 📂 Project Structure

```text
LIBRARY-MANAGEMENT-SYSTEM/
│
├── src/
│   ├── exception/
│   ├── model/
│   ├── service/
│   ├── util/
│   ├── library_data.ser
│   └── Main.java
│
├── test/
│   └── service/
│       ├── FineCalculatorTest.java
│       └── LibraryServiceTest.java
│
├── screenshots/
│   ├── main_menu.png
│   ├── search_results.png
│   └── issue_book_result.png
│
├── README.md
└── statement.md
```

## ▶️ Quick Start

For an evaluator who wants to run the application quickly:

```bash
git clone https://github.com/utkrishtsingh88-bit/LIBRARY-MANAGEMENT-SYSTEM.git
cd LIBRARY-MANAGEMENT-SYSTEM/src
javac model/*.java service/*.java exception/*.java util/*.java Main.java
java Main
```

No external database or runtime library is required.

## 👨‍💻 Author

**Utkrisht Singh**

---

⭐ **If you found this project useful, consider giving it a star!**