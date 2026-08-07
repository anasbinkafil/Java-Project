package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * DataStore singleton managing persistent data storage using local .txt files.
 */
public class DataStore {
    private static DataStore instance;

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/users.txt";
    private static final String INVENTORY_FILE = "data/inventory.txt";
    private static final String REQUESTS_FILE = "data/requests.txt";

    private List<User> users;
    private List<BloodInventory> inventories;
    private List<BloodRequest> requests;
    private int nextRequestId = 1001;

    private DataStore() {
        users = new ArrayList<>();
        inventories = new ArrayList<>();
        requests = new ArrayList<>();
        
        // Ensure data folder and text files exist
        initDataFiles();
        loadDataFromFiles();
    }

    // Thread-safe singleton instance
    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Create data folder and files if missing
    private void initDataFiles() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            File uFile = new File(USERS_FILE);
            File iFile = new File(INVENTORY_FILE);
            File rFile = new File(REQUESTS_FILE);

            if (!uFile.exists()) {
                uFile.createNewFile();
                seedDefaultUsers();
            }
            if (!iFile.exists()) {
                iFile.createNewFile();
                seedDefaultInventory();
            }
            if (!rFile.exists()) {
                rFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing data files: " + e.getMessage());
        }
    }

    // Seed initial users into text file
    private void seedDefaultUsers() {
        saveUserToFile(new User("Demo User", "demo@bloodbank.org", "01700000000", "A+", "admin", "1234"));
    }

    // Seed default inventory records into text file
    private void seedDefaultInventory() {
        List<BloodInventory> defaults = new ArrayList<>();
        defaults.add(new BloodInventory("A+", "DHANMONDI", "SQUARE HOSPITAL", 1.5, 22));
        defaults.add(new BloodInventory("A-", "DHANMONDI", "SQUARE HOSPITAL", 1.5, 8));
        defaults.add(new BloodInventory("B+", "DHANMONDI", "LABAID HOSPITAL", 2.0, 14));
        defaults.add(new BloodInventory("O+", "DHANMONDI", "BRB HOSPITAL", 1.2, 30));

        defaults.add(new BloodInventory("O+", "PURAN DHAKA", "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL", 1.8, 15));
        defaults.add(new BloodInventory("AB+", "PURAN DHAKA", "NATIONAL MEDICAL COLLEGE HOSPITAL", 2.1, 10));
        defaults.add(new BloodInventory("B-", "PURAN DHAKA", "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL", 1.8, 5));

        defaults.add(new BloodInventory("B+", "MIRPUR", "EVERCARE HOSPITAL", 2.8, 18));
        defaults.add(new BloodInventory("A+", "MIRPUR", "DELTA MEDICAL COLLEGE HOSPITAL", 3.0, 12));
        defaults.add(new BloodInventory("O-", "MIRPUR", "EVERCARE HOSPITAL", 2.8, 4));

        defaults.add(new BloodInventory("O+", "SAVAR", "ENAM MEDICAL COLLEGE HOSPITAL", 2.2, 20));
        defaults.add(new BloodInventory("A+", "SAVAR", "ENAM MEDICAL COLLEGE HOSPITAL", 2.2, 16));

        defaults.add(new BloodInventory("AB+", "BANANI", "UNITED HOSPITAL", 1.3, 25));
        defaults.add(new BloodInventory("B+", "BANANI", "UNITED HOSPITAL", 1.3, 19));
        defaults.add(new BloodInventory("A+", "BANANI", "KURMITOLA GENERAL HOSPITAL", 2.5, 21));

        for (BloodInventory item : defaults) {
            saveInventoryToFile(item);
        }
    }

    // Load data from users.txt, inventory.txt, requests.txt into memory lists
    private void loadDataFromFiles() {
        users.clear();
        inventories.clear();
        requests.clear();

        // Load users
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users.txt: " + e.getMessage());
        }

        // Load inventory
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    inventories.add(new BloodInventory(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading inventory.txt: " + e.getMessage());
        }

        // Load requests
        int maxId = 1000;
        try (BufferedReader br = new BufferedReader(new FileReader(REQUESTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 8) {
                    BloodRequest req = new BloodRequest(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5], parts[6], parts[7]);
                    requests.add(req);
                    try {
                        int idNum = Integer.parseInt(parts[0]);
                        if (idNum > maxId) maxId = idNum;
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading requests.txt: " + e.getMessage());
        }
        nextRequestId = maxId + 1;
    }

    // Append new user to users.txt
    private void saveUserToFile(User u) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(u.getFullName() + ";" + u.getEmail() + ";" + u.getPhone() + ";" + u.getBloodGroup() + ";" + u.getUsername() + ";" + u.getPassword());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to users.txt: " + e.getMessage());
        }
    }

    // Append inventory to inventory.txt
    private void saveInventoryToFile(BloodInventory item) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE, true))) {
            bw.write(item.getBloodGroup() + ";" + item.getLocation() + ";" + item.getHospitalName() + ";" + item.getDistanceKm() + ";" + item.getAvailableBags());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to inventory.txt: " + e.getMessage());
        }
    }

    // Append request to requests.txt
    private void saveRequestToFile(BloodRequest req) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REQUESTS_FILE, true))) {
            bw.write(req.getRequestId() + ";" + req.getPatientName() + ";" + req.getBloodGroup() + ";" + req.getBloodBags() + ";" + req.getLocation() + ";" + req.getHospital() + ";" + req.getContactNo() + ";" + req.getStatus());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to requests.txt: " + e.getMessage());
        }
    }

    // Public API methods
    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }
        users.add(user);
        saveUserToFile(user);
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
            if ((bloodGroup.isEmpty() || item.getBloodGroup().equalsIgnoreCase(bloodGroup)) &&
                (location.isEmpty() || item.getLocation().equalsIgnoreCase(location))) {
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
        saveRequestToFile(request);
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
