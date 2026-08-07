package controller;

import model.BloodRequest;
import model.DataStore;

public class RequestController {
    private DataStore dataStore;

    public RequestController() {
        this.dataStore = DataStore.getInstance();
    }

    public BloodRequest submitRequest(String patientName, String bloodGroup, String bagsStr, String location, String hospital, String contactNo) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient Name cannot be empty.");
        }

        int bags;
        try {
            bags = Integer.parseInt(bagsStr.trim());
            if (bags <= 0) {
                throw new IllegalArgumentException("Blood bags requested must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Blood Bags must be a valid number.");
        }

        if (contactNo == null || contactNo.trim().length() < 7) {
            throw new IllegalArgumentException("Please enter a valid Contact Number.");
        }

        String requestId = dataStore.generateRequestId();
        BloodRequest request = new BloodRequest(requestId, patientName.trim(), bloodGroup, bags, location, hospital, contactNo.trim(), "PENDING");
        dataStore.addBloodRequest(request);

        return request;
    }
}
