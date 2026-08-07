package controller;

import model.DataStore;
import model.User;

/**
 * Controller handling user registration and login validation across roles.
 */
public class AuthController {
    private DataStore dataStore;

    public AuthController() {
        this.dataStore = DataStore.getInstance();
    }

    public String registerUser(String fullName, String email, String phone, String bloodGroup, String username, String password) {
        return registerUser(fullName, email, phone, bloodGroup, username, password, "PATIENT");
    }

    // Validates inputs and registers new user with role
    public String registerUser(String fullName, String email, String phone, String bloodGroup, String username, String password, String role) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full Name cannot be empty.";
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return "Please enter a valid Email address.";
        }
        if (phone == null || phone.trim().length() < 7) {
            return "Please enter a valid Phone number.";
        }
        if (bloodGroup == null || bloodGroup.trim().isEmpty()) {
            return "Please select or enter a Blood Group.";
        }
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.trim().length() < 4) {
            return "Password must be at least 4 characters long.";
        }

        User newUser = new User(fullName.trim(), email.trim(), phone.trim(), bloodGroup.trim(), username.trim(), password, role);
        boolean success = dataStore.addUser(newUser);

        if (!success) {
            return "Username already exists. Please choose a different username.";
        }

        NavigationController.getInstance().setCurrentUser(newUser);
        return null;
    }

    // Authenticates credentials
    public User loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        User user = dataStore.authenticateUser(username.trim(), password);
        if (user != null) {
            NavigationController.getInstance().setCurrentUser(user);
        }
        return user;
    }
}
