# Blood Bank Management System (MVC Architecture)

A Java-based desktop application built using Java Swing following the **Model-View-Controller (MVC)** design pattern for managing a Blood Bank system, styled after the Stitch MCP **Clinical Integrity** design system.

## Folder Structure

```
Java-Project/
├── docs/                             # System Documentation & Architecture Specs
│   ├── ARCHITECTURE.md               # Detailed MVC Architecture breakdown
│   └── SYSTEM_PLAN.md                # System workflow & specifications
├── src/                              # Source code package roots
│   ├── model/                        # Model Layer (Entities & Data Access)
│   │   ├── User.java                 # User entity (FullName, Email, Phone, BloodGroup, Username, Password)
│   │   ├── BloodInventory.java       # Inventory entity (BloodGroup, Location, Hospital, Distance, Bags)
│   │   ├── BloodRequest.java         # Blood Request entity (RequestId, Patient, Group, Bags, Status)
│   │   └── DataStore.java            # Centralized in-memory repository singleton
│   ├── view/                         # Viewer Layer (Swing GUI Screens & UI Theme)
│   │   ├── UITheme.java              # Design System color tokens & UI component styling
│   │   ├── WelcomeView.java          # Landing & Welcome screen
│   │   ├── LoginView.java            # User Authentication screen
│   │   ├── RegistrationView.java     # User Registration form
│   │   ├── SearchBloodView.java      # Search & filter blood stock
│   │   ├── RequestBloodView.java     # Submit patient blood request form
│   │   └── MyRequestView.java        # Submitted request details screen
│   ├── controller/                   # Controller Layer (Business Logic & Handlers)
│   │   ├── AuthController.java       # Registration & Login validation logic
│   │   ├── SearchController.java     # Inventory search query engine
│   │   ├── RequestController.java    # Blood request handler & ID generator
│   │   └── NavigationController.java # Frame switching & session controller
│   └── Start.java                    # Application Bootstrap Launcher
└── bin/                              # Compiled bytecodes (.class files)
```

## Features

- **Model-View-Controller Architecture**: Complete separation of UI, business logic, and data entities into dedicated packages (`model`, `view`, `controller`).
- **Clinical Integrity Design Tokens**: Styled using `#D32F2F` Crimson Red, `#00796B` Teal accents, and `#181C1E` dark header banners.
- **User Authentication**: Secure register & login system with credential validation.
- **Search Blood Inventory**: Search available blood bags across various hospitals and locations (Dhanmondi, Mirpur, Banani, Puran Dhaka, Savar).
- **Request Blood**: Submit blood request with patient details, contact numbers, and track request status.

## How to Build and Run

1. Open Command Prompt / PowerShell in the `Java-Project` directory.
2. Compile all source files into the `bin` directory:
   ```cmd
   cmd /c "dir /s /b src\*.java > sources.txt && javac -d bin @sources.txt"
   ```
3. Run the application:
   ```cmd
   java -cp bin Start
   ```
