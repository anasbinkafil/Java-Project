package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * DataStore singleton managing persistent data storage using local text files (Record.txt, users.txt, inventory.txt, requests.txt)
 * following the file I/O structure of the reference project.
 */
public class DataStore {
    private static DataStore instance;

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/users.txt";
    private static final String INVENTORY_FILE = "data/inventory.txt";
    private static final String REQUESTS_FILE = "data/requests.txt";
    private static final String RECORD_FILE = "data/Record.txt";

    private List<User> users;
    private List<BloodInventory> inventories;
    private List<BloodRequest> requests;
    private List<Donor> donors;
    private int nextRequestId = 1001;

    private DataStore() {
        users = new ArrayList<>();
        inventories = new ArrayList<>();
        requests = new ArrayList<>();
        donors = new ArrayList<>();
        
        initDataFiles();
        loadDataFromFiles();
        readDonorsFromFile();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void initDataFiles() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            File uFile = new File(USERS_FILE);
            File iFile = new File(INVENTORY_FILE);
            File rFile = new File(REQUESTS_FILE);
            File recFile = new File(RECORD_FILE);

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
            if (!recFile.exists()) {
                recFile.createNewFile();
                seedDefaultDonors();
            }
        } catch (IOException e) {
            System.err.println("Error initializing data files: " + e.getMessage());
        }
    }

    private void seedDefaultUsers() {
        saveUserToFile(new User("System Admin", "admin@bloodbank.org", "01700000000", "O+", "admin", "1234", "ADMIN"));
        saveUserToFile(new User("John Donor", "john@donor.org", "01800000000", "A+", "donor1", "1234", "DONOR"));
    }

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

    private void seedDefaultDonors() {
        addDonor(new Donor("Kamal Hossain", "28", "Male", "kamal@gmail.com", "01711223344", "Dhanmondi", "B-"));
        addDonor(new Donor("Rafiqul Islam", "32", "Male", "rafiq@gmail.com", "01822334455", "Mirpur", "A+"));
        addDonor(new Donor("Nusrat Jahan", "25", "Female", "nusrat@gmail.com", "01933445566", "Banani", "O+"));
    }

    // Load data from files
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
                    String role = (parts.length >= 7) ? parts[6] : "PATIENT";
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], role));
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

    // Read Donors from Record.txt using Scanner matching reference code style
    public void readDonorsFromFile() {
        donors.clear();
        try {
            File f = new File(RECORD_FILE);
            if (!f.exists()) return;
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String name = s.nextLine();
                if (!s.hasNextLine()) break;
                String age = s.nextLine();
                if (!s.hasNextLine()) break;
                String sex = s.nextLine();
                if (!s.hasNextLine()) break;
                String email = s.nextLine();
                if (!s.hasNextLine()) break;
                String pn = s.nextLine();
                if (!s.hasNextLine()) break;
                String adr = s.nextLine();
                if (!s.hasNextLine()) break;
                String bg = s.nextLine();

                donors.add(new Donor(name, age, sex, email, pn, adr, bg));
            }
            s.close();
        } catch (Exception e) {
            System.err.println("Error reading Record.txt: " + e.getMessage());
        }
    }

    // Write Donors to Record.txt using FileWriter matching reference code style
    public void writeDonorsToFile() {
        try {
            File f = new File(RECORD_FILE);
            FileWriter fw = new FileWriter(f);
            for (Donor d : donors) {
                if (d != null) {
                    fw.write(d.getName() + "\n");
                    fw.write(d.getAge() + "\n");
                    fw.write(d.getSex() + "\n");
                    fw.write(d.getEmail() + "\n");
                    fw.write(d.getPn() + "\n");
                    fw.write(d.getAdr() + "\n");
                    fw.write(d.getBg() + "\n");
                }
            }
            fw.close();
        } catch (Exception e) {
            System.err.println("Error writing Record.txt: " + e.getMessage());
        }
    }

    public synchronized void addDonor(Donor d) {
        donors.add(d);
        writeDonorsToFile();
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public synchronized boolean deleteDonor(int index) {
        if (index >= 0 && index < donors.size()) {
            donors.remove(index);
            writeDonorsToFile();
            return true;
        }
        return false;
    }

    // Save helpers
    private void saveUserToFile(User u) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(u.getFullName() + ";" + u.getEmail() + ";" + u.getPhone() + ";" + u.getBloodGroup() + ";" + u.getUsername() + ";" + u.getPassword() + ";" + u.getRole());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to users.txt: " + e.getMessage());
        }
    }

    private void saveInventoryToFile(BloodInventory item) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE, true))) {
            bw.write(item.getBloodGroup() + ";" + item.getLocation() + ";" + item.getHospitalName() + ";" + item.getDistanceKm() + ";" + item.getAvailableBags());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to inventory.txt: " + e.getMessage());
        }
    }

    private void saveRequestToFile(BloodRequest req) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REQUESTS_FILE, true))) {
            bw.write(req.getRequestId() + ";" + req.getPatientName() + ";" + req.getBloodGroup() + ";" + req.getBloodBags() + ";" + req.getLocation() + ";" + req.getHospital() + ";" + req.getContactNo() + ";" + req.getStatus());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to requests.txt: " + e.getMessage());
        }
    }

    public synchronized void saveAllUsersToFiles() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, false))) {
            for (User u : users) {
                bw.write(u.getFullName() + ";" + u.getEmail() + ";" + u.getPhone() + ";" + u.getBloodGroup() + ";" + u.getUsername() + ";" + u.getPassword() + ";" + u.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error rewriting users.txt: " + e.getMessage());
        }
    }

    public synchronized void saveAllInventoryToFiles() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE, false))) {
            for (BloodInventory item : inventories) {
                bw.write(item.getBloodGroup() + ";" + item.getLocation() + ";" + item.getHospitalName() + ";" + item.getDistanceKm() + ";" + item.getAvailableBags());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error rewriting inventory.txt: " + e.getMessage());
        }
    }

    public synchronized void saveAllRequestsToFiles() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REQUESTS_FILE, false))) {
            for (BloodRequest req : requests) {
                bw.write(req.getRequestId() + ";" + req.getPatientName() + ";" + req.getBloodGroup() + ";" + req.getBloodBags() + ";" + req.getLocation() + ";" + req.getHospital() + ";" + req.getContactNo() + ";" + req.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error rewriting requests.txt: " + e.getMessage());
        }
    }

    public synchronized void addOrUpdateInventory(String bloodGroup, String location, String hospitalName, double distanceKm, int additionalBags) {
        boolean found = false;
        for (BloodInventory item : inventories) {
            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup) &&
                item.getLocation().equalsIgnoreCase(location) &&
                item.getHospitalName().equalsIgnoreCase(hospitalName)) {
                item.setAvailableBags(item.getAvailableBags() + additionalBags);
                found = true;
                break;
            }
        }
        if (!found) {
            BloodInventory newItem = new BloodInventory(bloodGroup.toUpperCase(), location.toUpperCase(), hospitalName.toUpperCase(), distanceKm, additionalBags);
            inventories.add(newItem);
        }
        saveAllInventoryToFiles();
    }

    public synchronized boolean deleteInventoryItem(int index) {
        if (index >= 0 && index < inventories.size()) {
            inventories.remove(index);
            saveAllInventoryToFiles();
            return true;
        }
        return false;
    }

    public synchronized boolean updateRequestStatus(String requestId, String newStatus) {
        for (BloodRequest req : requests) {
            if (req.getRequestId().equalsIgnoreCase(requestId)) {
                req.setStatus(newStatus.toUpperCase());
                
                if ("FULFILLED".equalsIgnoreCase(newStatus) || "APPROVED".equalsIgnoreCase(newStatus)) {
                    for (BloodInventory item : inventories) {
                        if (item.getBloodGroup().equalsIgnoreCase(req.getBloodGroup()) &&
                            item.getLocation().equalsIgnoreCase(req.getLocation()) &&
                            item.getAvailableBags() >= req.getBloodBags()) {
                            item.setAvailableBags(item.getAvailableBags() - req.getBloodBags());
                            saveAllInventoryToFiles();
                            break;
                        }
                    }
                }
                saveAllRequestsToFiles();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean deleteBloodRequest(String requestId) {
        boolean removed = requests.removeIf(req -> req.getRequestId().equalsIgnoreCase(requestId));
        if (removed) {
            saveAllRequestsToFiles();
        }
        return removed;
    }

    public synchronized boolean updateUserRole(String username, String newRole) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                u.setRole(newRole);
                saveAllUsersToFiles();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean deleteUser(String username) {
        boolean removed = users.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
        if (removed) {
            saveAllUsersToFiles();
        }
        return removed;
    }

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

    public List<User> getUsers() {
        return users;
    }

    public List<BloodInventory> searchInventory(String bloodGroup, String location) {
        List<BloodInventory> results = new ArrayList<>();
        for (BloodInventory item : inventories) {
            if ((bloodGroup == null || bloodGroup.isEmpty() || item.getBloodGroup().equalsIgnoreCase(bloodGroup)) &&
                (location == null || location.isEmpty() || item.getLocation().equalsIgnoreCase(location))) {
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

    public List<BloodInventory> getAllInventories() {
        return inventories;
    }

    public BloodRequest getLatestRequest() {
        if (requests.isEmpty()) {
            return null;
        }
        return requests.get(requests.size() - 1);
    }
}
