# Blood Bank Management System - System Specifications & Plan

## Overview
The Blood Bank Management System is a desktop application designed in Java Swing using the Model-View-Controller (MVC) architectural pattern. It allows users to register, log in, search for blood availability across hospitals and locations, request blood, and track submitted requests.

## Component Breakdown

### 1. Model Layer (`model`)
- **`User`**: Represents a system user (donors/requesters). Holds `fullName`, `email`, `phone`, `bloodGroup`, `username`, `password`.
- **`BloodInventory`**: Represents available blood bags by group, location, hospital, distance, and quantity.
- **`BloodRequest`**: Represents a patient request for blood. Tracks `requestId`, `patientName`, `bloodGroup`, `bagsNeeded`, `location`, `hospitalName`, `contactNo`, and `status`.
- **`DataStore`**: In-memory data repository initializing seed data and supporting querying, insertion, and updating of records.

### 2. View Layer (`view`)
- **`WelcomeView`**: Main landing window with navigation to Login and Registration.
- **`LoginView`**: Dialog window for user login credentials.
- **`RegistrationView`**: Registration form layout for new users.
- **`SearchBloodView`**: Form to filter blood inventory by group and location with dynamic result display.
- **`RequestBloodView`**: Form to submit a blood donation/request.
- **`MyRequestView`**: Screen showing active blood request details.

### 3. Controller Layer (`controller`)
- **`NavigationController`**: Manages window visibility, opening new views, disposing old views, and tracking current user session context.
- **`AuthController`**: Handles login credential validation and user registration rules.
- **`SearchController`**: Processes inventory search requests and returns formatted inventory data.
- **`RequestController`**: Validates blood request submissions and persists new requests.

## Data Flow Diagram
1. **User Launch** -> `Start.java` initializes `DataStore` -> opens `WelcomeView`.
2. **Registration** -> `RegistrationView` captures input -> `AuthController` validates -> `DataStore` saves `User` -> opens `LoginView` or `SearchBloodView`.
3. **Login** -> `LoginView` captures credentials -> `AuthController` checks `DataStore` -> opens `SearchBloodView` on success.
4. **Search Blood** -> `SearchBloodView` passes choices to `SearchController` -> queries `DataStore` -> renders inventory details in view.
5. **Request Blood** -> `RequestBloodView` captures details -> `RequestController` validates & saves to `DataStore` -> opens `MyRequestView`.
