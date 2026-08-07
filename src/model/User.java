package model;

/**
 * Model class representing a user, donor, or admin entity.
 */
public class User {
    private String fullName;
    private String email;
    private String phone;
    private String bloodGroup;
    private String username;
    private String password;
    private String role; // "PATIENT", "DONOR", "ADMIN"

    public User(String fullName, String email, String phone, String bloodGroup, String username, String password) {
        this(fullName, email, phone, bloodGroup, username, password, "PATIENT");
    }

    public User(String fullName, String email, String phone, String bloodGroup, String username, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.username = username;
        this.password = password;
        this.role = (role == null || role.trim().isEmpty()) ? "PATIENT" : role.toUpperCase().trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role) || "MANAGEMENT".equalsIgnoreCase(role);
    }

    public boolean isDonor() {
        return "DONOR".equalsIgnoreCase(role);
    }
}
