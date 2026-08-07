# Blood Bank Management System - Full Codebase Explanation

## 1. Project Overview

This is a **Java Swing desktop application** that manages a Blood Bank system. It allows users to register, log in, search for available blood across hospitals and locations, request blood for patients, and view submitted request details. The entire application follows the **Model-View-Controller (MVC)** architectural pattern, ensuring clean separation between data, UI, and business logic.

---

## 2. MVC Architecture

The project is divided into three main packages:

```
src/
├── model/         → Data entities and in-memory data store
├── view/          → Swing GUI screens (user interface)
├── controller/    → Business logic, validation, and navigation
└── Start.java     → Application entry point
```

---

## 3. Model Layer (`model/`)

The model layer contains **4 classes** that represent data entities and the data storage system. These classes contain **no Swing/UI code** — they are pure data objects.

### 3.1 `User.java` — User Entity

Represents a registered user in the system.

| Field          | Type     | Description                              |
|----------------|----------|------------------------------------------|
| `fullName`     | String   | The user's full name                     |
| `email`        | String   | User's email address                     |
| `phone`        | String   | Contact phone number                     |
| `bloodGroup`   | String   | User's blood group (A+, B-, O+, etc.)    |
| `username`     | String   | Unique login username                    |
| `password`     | String   | Login password                           |

All fields have getter and setter methods. This class is instantiated when a new user registers and also used when a user logs in (stored as `currentUser` in `NavigationController`).

### 3.2 `BloodInventory.java` — Inventory Entity

Represents an available blood bag record at a specific hospital and location.

| Field         | Type   | Description                          |
|---------------|--------|--------------------------------------|
| `bloodGroup`  | String | Blood group (A+, O-, etc.)           |
| `location`    | String | Area name (Dhanmondi, Mirpur, etc.)  |
| `hospitalName`| String | Hospital where blood is available    |
| `distanceKm`  | double | Distance in kilometers               |
| `availableBags`| int   | Number of available blood bags       |

This entity is used exclusively by `SearchController` and `DataStore` to return search results.

### 3.3 `BloodRequest.java` — Blood Request Entity

Represents a patient's blood request submission.

| Field          | Type   | Description                             |
|----------------|--------|-----------------------------------------|
| `requestId`    | String | Auto-generated unique ID (1001, 1002...)|
| `patientName`  | String | Name of the patient needing blood       |
| `bloodGroup`   | String | Required blood group                    |
| `bloodBags`    | int    | Number of bags requested                |
| `location`     | String | Desired location                        |
| `hospital`     | String | Preferred hospital                      |
| `contactNo`    | String | Contact phone number                    |
| `status`       | String | Request status ("PENDING" by default)   |

Status is set to `"PENDING"` upon creation. This entity is passed from `RequestBloodView` → `RequestController` → `DataStore`, and then displayed in `MyRequestView`.

### 3.4 `DataStore.java` — Central In-Memory Repository (Singleton)

This is the **heart of the data layer**. It is a **Singleton** — meaning only one instance exists throughout the entire application's lifecycle. It stores all data in three `ArrayList`s:

- `users` → `List<User>`
- `inventories` → `List<BloodInventory>`
- `requests` → `List<BloodRequest>`

**Key Methods:**

| Method                              | Purpose                                                                 |
|-------------------------------------|-------------------------------------------------------------------------|
| `getInstance()`                     | Returns the single shared DataStore instance (thread-safe via `synchronized`) |
| `seedData()`                        | Populates initial demo data: 1 default user and 15 blood inventory entries across 5 locations |
| `addUser(User)`                     | Adds a new user; returns `false` if username already exists            |
| `authenticateUser(username, password)` | Finds and returns a `User` matching credentials, or `null` if invalid |
| `searchInventory(bloodGroup, location)` | Filters inventory by blood group AND location, returns matching list |
| `generateRequestId()`               | Returns an auto-incrementing request ID starting from `1001`           |
| `addBloodRequest(BloodRequest)`     | Persists a new blood request                                          |
| `getRequests()`                     | Returns all submitted blood requests                                   |
| `getLatestRequest()`                | Returns the most recently submitted request                            |

The `seedData()` method populates the inventory with real hospital names in Dhaka, Bangladesh locations (Dhanmondi, Mirpur, Banani, Puran Dhaka, Savar), providing a realistic starting dataset including hospitals like Square Hospital, United Hospital, Evercare, etc.

---

## 4. Controller Layer (`controller/`)

Controllers act as the **bridge between Views and Models**. They receive input from Views, validate it, interact with the DataStore, and instruct the NavigationController to switch screens.

### 4.1 `NavigationController.java` — Frame Manager (Singleton)

Manages the **opening and closing of application windows (JFrame)**. It also stores the `currentUser` for session tracking.

**Why Singleton?** Every other controller and view needs access to the same navigation instance to switch between screens.

**Key Methods:**

| Method                              | What it does                                                                 |
|-------------------------------------|------------------------------------------------------------------------------|
| `getInstance()`                     | Returns the single NavigationController instance                             |
| `getCurrentUser()` / `setCurrentUser()` | Gets/sets the currently logged-in `User` object                           |
| `openWelcomeView(JFrame)`           | Closes the current frame, opens `WelcomeView`                                |
| `openLoginView(JFrame)`             | Closes current frame, opens `LoginView`                                      |
| `openRegistrationView(JFrame)`      | Closes current frame, opens `RegistrationView`                               |
| `openSearchBloodView(JFrame)`       | Closes current frame, opens `SearchBloodView`                                |
| `openRequestBloodView(JFrame)`      | Closes current frame, opens `RequestBloodView`                               |
| `openMyRequestView(JFrame, BloodRequest)` | Closes current frame, opens `MyRequestView` with the submitted request |

Every method accepts a `JFrame` parameter — the **current frame being replaced** — so it can `dispose()` it (free memory) before creating the new view.

### 4.2 `AuthController.java` — Authentication & Registration Logic

Handles user registration validation and login credential checking.

**`registerUser(...)` method flow:**
1. Validates each field: full name not empty, email contains `@` and `.`, phone ≥ 7 chars, blood group selected, username not empty, password ≥ 4 chars.
2. Creates a new `User` object and calls `DataStore.addUser()`.
3. If username already exists, returns an error message string.
4. If successful, sets the new user as `currentUser` via `NavigationController` and returns `null` (indicating success).

**`loginUser(username, password)` method flow:**
1. Validates that username and password are not empty.
2. Calls `DataStore.authenticateUser()` to check credentials.
3. If found, sets the user as `currentUser` via `NavigationController`.
4. Returns the `User` object on success, or `null` on failure.

### 4.3 `SearchController.java` — Blood Search Engine

Processes blood inventory search queries.

**`searchBlood(bloodGroup, location)` method flow:**
1. Validates that both parameters are not null.
2. Calls `DataStore.searchInventory()` to get filtered results.
3. If no results found, returns a friendly message suggesting the user submit a blood request.
4. If results found, formats each result into a readable string showing: blood group, location, hospital name, distance (KM), and available bags count.

### 4.4 `RequestController.java` — Blood Request Handler

Validates and processes blood request submissions.

**`submitRequest(...)` method flow:**
1. Validates: patient name not empty, blood bags is a valid positive integer, contact number ≥ 7 chars.
2. Calls `DataStore.generateRequestId()` to get a unique request ID.
3. Creates a new `BloodRequest` with status `"PENDING"`.
4. Persists it to `DataStore` via `addBloodRequest()`.
5. Returns the created `BloodRequest` object to the view for display.

---

## 5. View Layer (`view/`)

Views are **pure Swing GUI frames**. They define the visual layout (labels, text fields, combo boxes, buttons) and capture user actions via `ActionListener`. They **never contain business logic** — they delegate all logic to Controllers.

### 5.1 `WelcomeView.java` — Landing Screen

The first screen the user sees after launching the app.

- **Title:** "BLOOD BANK MANAGEMENT SYSTEM"
- **Buttons:** Login, Register
- **Action:**
  - Clicking **Register** → calls `NavigationController.openRegistrationView(this)`
  - Clicking **Login** → calls `NavigationController.openLoginView(this)`

### 5.2 `LoginView.java` — Login Screen

Prompts for username and password.

- **Fields:** Username text field, Password field
- **Buttons:** Login, Back
- **Action:**
  - On **Login**: reads username/password → calls `AuthController.loginUser()` → if successful, shows success message and navigates to `SearchBloodView`; if failed, shows error dialog
  - On **Back**: navigates back to `WelcomeView`

### 5.3 `RegistrationView.java` — Registration Form

Collects new user information.

- **Fields:** Full Name, Email, Phone, Blood Group (dropdown), Username, Password
- **Buttons:** Register, Clear, Back
- **Action:**
  - On **Register**: reads all fields → calls `AuthController.registerUser()` → if error returned, shows error; if success, shows confirmation and navigates to `SearchBloodView`
  - On **Clear**: resets all form fields
  - On **Back**: navigates to `WelcomeView`

### 5.4 `SearchBloodView.java` — Blood Search Screen

The main functional screen where users search for available blood.

- **Dropdowns:** Blood Group (A+ to O-), Location (Dhanmondi, Puran Dhaka, Mirpur, Savar, Banani)
- **Result Area:** A scrollable text area that displays formatted search results
- **Buttons:** Search, Request Blood, Clear, Back
- **Action:**
  - On **Search**: reads selected blood group and location → calls `SearchController.searchBlood()` → displays result in text area
  - On **Request Blood**: navigates to `RequestBloodView`
  - On **Clear**: resets dropdowns and clears result area
  - On **Back**: navigates to `WelcomeView`

### 5.5 `RequestBloodView.java` — Blood Request Form

Allows users to submit a blood request for a patient.

- **Fields:** Patient Name, Blood Group (dropdown), Blood Bags (number), Location (dropdown), Hospital (dropdown), Contact No.
- **Buttons:** Request, Clear, Back
- **Action:**
  - On **Request**: reads all fields → calls `RequestController.submitRequest()` → on success, shows request ID in dialog and navigates to `MyRequestView` with the created `BloodRequest` object
  - On **Clear**: resets all form fields
  - On **Back**: navigates back to `SearchBloodView`

### 5.6 `MyRequestView.java` — Request Details Screen

Displays the details of the most recently submitted blood request.

- Receives a `BloodRequest` object in its constructor.
- Displays: Request ID, Patient Name, Blood Group, Blood Bags, Location, Hospital, Contact No., Status
- **Button:** Back to Search → navigates back to `SearchBloodView`

---

## 6. Application Entry Point — `Start.java`

This is the **bootstrap class** containing `main()`. It performs exactly two actions:

1. Calls `DataStore.getInstance()` — this triggers the singleton initialization, which creates the ArrayLists and populates seed data (demo user + 15 inventory records).
2. Calls `NavigationController.getInstance().openWelcomeView(null)` — opens the WelcomeView as the first screen. The `null` argument means there is no previous frame to dispose.

---

## 7. How All Parts Connect — Complete Data Flow

### Flow 1: Application Launch
```
Start.main()
  → DataStore.getInstance()  [initializes singleton + seed data]
  → NavigationController.getInstance().openWelcomeView(null)
  → WelcomeView appears on screen
```

### Flow 2: User Registration
```
WelcomeView [click "Register"]
  → NavigationController.openRegistrationView(this)
  → RegistrationView appears
  [user fills form, clicks "Register"]
  → RegistrationView reads field values
  → AuthController.registerUser(name, email, phone, blood, username, password)
      → validates all fields
      → DataStore.addUser(newUser)  [persists user]
      → NavigationController.setCurrentUser(newUser)  [sets session]
  → RegistrationView receives error or null (success)
  → NavigationController.openSearchBloodView(this)
  → SearchBloodView appears
```

### Flow 3: User Login
```
WelcomeView [click "Login"]
  → NavigationController.openLoginView(this)
  → LoginView appears
  [user enters credentials, clicks "Login"]
  → LoginView reads username + password
  → AuthController.loginUser(username, password)
      → validates non-empty
      → DataStore.authenticateUser(username, password)  [returns User or null]
      → NavigationController.setCurrentUser(user)
  → LoginView receives User or null
  → NavigationController.openSearchBloodView(this)  [on success]
  → SearchBloodView appears
```

### Flow 4: Search Blood
```
SearchBloodView [selects blood group + location, clicks "Search"]
  → SearchController.searchBlood(bloodGroup, location)
      → DataStore.searchInventory(bloodGroup, location)  [returns filtered list]
      → formats results into readable string
  → SearchBloodView displays formatted string in resultArea
```

### Flow 5: Request Blood
```
SearchBloodView [clicks "Request Blood"]
  → NavigationController.openRequestBloodView(this)
  → RequestBloodView appears
  [user fills form, clicks "Request"]
  → RequestBloodView reads all field values
  → RequestController.submitRequest(patientName, bloodGroup, bags, location, hospital, contact)
      → validates all fields
      → DataStore.generateRequestId()  [gets next ID like "1001"]
      → creates new BloodRequest with status "PENDING"
      → DataStore.addBloodRequest(request)  [persists request]
      → returns BloodRequest object
  → RequestBloodView receives BloodRequest
  → NavigationController.openMyRequestView(this, request)
  → MyRequestView displays all request details
```

### Flow 6: Back Navigation
```
MyRequestView [clicks "Back to Search"]
  → NavigationController.openSearchBloodView(this)
  → SearchBloodView appears
```

---

## 8. Key Design Patterns Used

### Singleton Pattern
Used in **three classes**: `DataStore`, `NavigationController`. Both use `private` constructors and a `public static synchronized getInstance()` method. This ensures:
- Only one `DataStore` exists — all controllers share the same data
- Only one `NavigationController` exists — all views use the same navigation logic
- `currentUser` session is stored in a single place

### MVC Pattern
The project strictly follows MVC:
- **Model** (`model/`): Pure data, zero UI dependency
- **View** (`view/`): Pure UI, zero business logic (delegates to controllers)
- **Controller** (`controller/`): Contains all business logic, sits between View and Model

---

## 9. Summary of Interconnections

```
                    ┌──────────────────┐
                    │    Start.java    │  (Entry Point)
                    │   main() method  │
                    └────────┬─────────┘
                             │
              ┌──────────────▼──────────────┐
              │      DataStore (Singleton)  │
              │  - users, inventories,      │
              │    requests lists           │
              │  - seedData()               │
              │  - CRUD methods             │
              └──────┬─────────────┬────────┘
                     │             │
          ┌──────────▼──┐   ┌─────▼──────────┐
          │ AuthController│   │ SearchController│
          │ - registerUser│   │ - searchBlood   │
          │ - loginUser   │   └────────────────┘
          └──────┬───────┘
                 │
          ┌──────▼───────┐
          │ RequestController│
          │ - submitRequest  │
          └──────┬──────────┘
                 │
    ┌────────────▼──────────────────┐
    │   NavigationController (Singleton) │
    │  - Manages JFrame switching       │
    │  - Stores currentUser session     │
    └────────────┬──────────────────┘
                 │ opens/closes
    ┌────────────▼──────────────────┐
    │      View Layer (6 screens)    │
    │  Welcome → Login → Register    │
    │  Search → Request → MyRequest  │
    └───────────────────────────────┘
```

Every user action flows: **View → Controller → Model (DataStore) → View updates**. Navigation between screens is always orchestrated by `NavigationController`, which disposes the old frame and creates the new one.
