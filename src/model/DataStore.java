package model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;

    private List<User> users;
    private List<BloodInventory> inventories;
    private List<BloodRequest> requests;
    private int nextRequestId = 1001;

    private DataStore() {
        users = new ArrayList<>();
        inventories = new ArrayList<>();
        requests = new ArrayList<>();
        seedData();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void seedData() {
        // Seed default demo user
        users.add(new User("Demo User", "demo@bloodbank.org", "01700000000", "A+", "admin", "1234"));

        // Seed locations & hospitals
        inventories.add(new BloodInventory("A+", "DHANMONDI", "SQUARE HOSPITAL", 1.5, 22));
        inventories.add(new BloodInventory("A-", "DHANMONDI", "SQUARE HOSPITAL", 1.5, 8));
        inventories.add(new BloodInventory("B+", "DHANMONDI", "LABAID HOSPITAL", 2.0, 14));
        inventories.add(new BloodInventory("O+", "DHANMONDI", "BRB HOSPITAL", 1.2, 30));

        inventories.add(new BloodInventory("O+", "PURAN DHAKA", "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL", 1.8, 15));
        inventories.add(new BloodInventory("AB+", "PURAN DHAKA", "NATIONAL MEDICAL COLLEGE HOSPITAL", 2.1, 10));
        inventories.add(new BloodInventory("B-", "PURAN DHAKA", "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL", 1.8, 5));

        inventories.add(new BloodInventory("B+", "MIRPUR", "EVERCARE HOSPITAL", 2.8, 18));
        inventories.add(new BloodInventory("A+", "MIRPUR", "DELTA MEDICAL COLLEGE HOSPITAL", 3.0, 12));
        inventories.add(new BloodInventory("O-", "MIRPUR", "EVERCARE HOSPITAL", 2.8, 4));

        inventories.add(new BloodInventory("O+", "SAVAR", "ENAM MEDICAL COLLEGE HOSPITAL", 2.2, 20));
        inventories.add(new BloodInventory("A+", "SAVAR", "ENAM MEDICAL COLLEGE HOSPITAL", 2.2, 16));

        inventories.add(new BloodInventory("AB+", "BANANI", "UNITED HOSPITAL", 1.3, 25));
        inventories.add(new BloodInventory("B+", "BANANI", "UNITED HOSPITAL", 1.3, 19));
        inventories.add(new BloodInventory("A+", "BANANI", "KURMITOLA GENERAL HOSPITAL", 2.5, 21));
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false; // Username already exists
            }
        }
        users.add(user);
        return true;
    }

    public User authenticateUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public List<BloodInventory> searchInventory(String bloodGroup, String location) {
        List<BloodInventory> results = new ArrayList<>();
        for (BloodInventory item : inventories) {
            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup) && item.getLocation().equalsIgnoreCase(location)) {
                results.add(item);
            }
        }
        return results;
    }

    public String generateRequestId() {
        return String.valueOf(nextRequestId++);
    }

    public void addBloodRequest(BloodRequest request) {
        requests.add(request);
    }

    public List<BloodRequest> getRequests() {
        return requests;
    }

    public BloodRequest getLatestRequest() {
        if (requests.isEmpty()) {
            return null;
        }
        return requests.get(requests.size() - 1);
    }
}
