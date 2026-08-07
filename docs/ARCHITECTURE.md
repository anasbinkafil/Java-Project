# MVC Architecture Documentation

## Package Structure

```
c:\Users\USERAS\Downloads\anas\Java-Project\
├── docs/
│   ├── ARCHITECTURE.md
│   └── SYSTEM_PLAN.md
└── src/
    ├── Start.java
    ├── model/
    │   ├── User.java
    │   ├── BloodInventory.java
    │   ├── BloodRequest.java
    │   └── DataStore.java
    ├── view/
    │   ├── WelcomeView.java
    │   ├── LoginView.java
    │   ├── RegistrationView.java
    │   ├── SearchBloodView.java
    │   ├── RequestBloodView.java
    │   └── MyRequestView.java
    └── controller/
        ├── NavigationController.java
        ├── AuthController.java
        ├── SearchController.java
        └── RequestController.java
```

## Layer Responsibilities

### 1. Model (`model.*`)
- Data classes holding domain entities (`User`, `BloodInventory`, `BloodRequest`).
- `DataStore` manages in-memory data structures, seed data initialization, lookup, insertion, and updates.
- Decoupled from Swing GUI (`javax.swing`) completely.

### 2. View (`view.*`)
- Pure Swing user interface frames.
- Defines UI components, layouts, titles, styling, labels, text fields, buttons, and combo boxes.
- Delegates action events to Controllers. Does NOT process business logic or access raw arrays directly.

### 3. Controller (`controller.*`)
- Receives user interactions from Views.
- Validates user input (email format, empty strings, password matching, bag numbers).
- Queries/Updates the `DataStore`.
- Instructs `NavigationController` on frame navigation.
