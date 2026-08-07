package controller;

import model.BloodInventory;
import model.DataStore;

import java.util.List;

/**
 * Controller handling blood inventory search and formatting.
 */
public class SearchController {
    private DataStore dataStore;

    public SearchController() {
        this.dataStore = DataStore.getInstance();
    }

    // Queries inventory records based on blood group & location filters
    public String searchBlood(String bloodGroup, String location) {
        if (bloodGroup == null || location == null) {
            return "Please select valid search parameters.";
        }

        List<BloodInventory> results = dataStore.searchInventory(bloodGroup, location);
        if (results.isEmpty()) {
            return "No matching blood inventory found for:\n" +
                   "Blood Group: " + bloodGroup + "\n" +
                   "Location: " + location + "\n\n" +
                   "Tip: You can submit a Blood Request directly!";
        }

        // Format inventory results
        StringBuilder builder = new StringBuilder();
        builder.append("=== SEARCH RESULTS (").append(results.size()).append(" Found) ===\n\n");

        for (int i = 0; i < results.size(); i++) {
            BloodInventory item = results.get(i);
            builder.append("Result #").append(i + 1).append(":\n");
            builder.append("  - Blood Group: ").append(item.getBloodGroup()).append("\n");
            builder.append("  - Location: ").append(item.getLocation()).append("\n");
            builder.append("  - Nearest Hospital: ").append(item.getHospitalName()).append("\n");
            builder.append("  - Distance: ").append(item.getDistanceKm()).append(" KM\n");
            builder.append("  - Available Blood Bags: ").append(item.getAvailableBags()).append("\n");
            builder.append("--------------------------------------------------\n");
        }

        return builder.toString();
    }
}
