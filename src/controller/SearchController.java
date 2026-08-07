package controller;

import model.BloodInventory;
import model.DataStore;

import java.util.List;

/**
 * Controller handling blood inventory search queries.
 */
public class SearchController {
    private DataStore dataStore;

    public SearchController() {
        this.dataStore = DataStore.getInstance();
    }

    // Returns matching inventory items for search filtering
    public List<BloodInventory> getMatchingInventory(String bloodGroup, String location) {
        String bg = (bloodGroup == null || bloodGroup.startsWith("Select")) ? "" : bloodGroup.trim();
        String loc = (location == null || location.startsWith("Select")) ? "" : location.trim();
        return dataStore.searchInventory(bg, loc);
    }

    // Formatted query summary text
    public String searchBlood(String bloodGroup, String location) {
        List<BloodInventory> results = getMatchingInventory(bloodGroup, location);
        if (results.isEmpty()) {
            return "No matching blood inventory found.";
        }

        StringBuilder builder = new StringBuilder();
        for (BloodInventory item : results) {
            builder.append(item.getHospitalName())
                   .append(" (").append(item.getLocation()).append(") - ")
                   .append(item.getBloodGroup()).append(": ")
                   .append(item.getAvailableBags()).append(" Bags available\n");
        }
        return builder.toString();
    }
}
