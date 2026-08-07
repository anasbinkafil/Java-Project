# Blood Bank Management System

This is a simple Java-based desktop application built using Java Swing for managing a Blood Bank system. The system allows users to register, search for blood, and request blood donations.

## Features

- **Registration System**: Users can register with their name, email, phone number, blood group, username, and password.
- **Login System**: Secure login for registered users (placeholder).
- **Search Blood**: Search functionality to find donors with specific blood groups.
- **Request Blood**: Users can submit requests for blood.
- **GUI**: Built using Java Swing components for a user-friendly desktop experience.

## Technology Stack

- **Language**: Java
- **UI Framework**: Java Swing (`javax.swing.*`, `java.awt.*`)

## Files in the Project

- `BloodBankManagementSystem.java`: The main entry screen of the application with options to login or register.
- `Registration.java`: The registration form UI for new users.
- `SearchBlood.java`: UI to search for a specific blood group.
- `RequestBlood.java`: UI to handle blood requests.
- `MyRequest.java`: UI to view/manage blood requests.
- `Start.java`: The main runner class containing the `public static void main(String[] args)` method to launch the application.

## How to Run

1. Make sure you have the Java Development Kit (JDK) installed on your system.
2. Compile all the Java files:
   ```bash
   javac *.java
   ```
3. Run the application via the `Start` class:
   ```bash
   java Start
   ```

## How to Push to GitHub

If you haven't pushed this project to GitHub yet, follow these commands in your project directory:

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/anasbinkafil/Java-Project.git
git push -u origin main
```
