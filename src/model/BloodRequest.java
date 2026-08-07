package model;

/**
 * Model class representing a submitted patient blood request.
 */
public class BloodRequest {
    private String requestId;
    private String patientName;
    private String bloodGroup;
    private int bloodBags;
    private String location;
    private String hospital;
    private String contactNo;
    private String status;

    public BloodRequest(String requestId, String patientName, String bloodGroup, int bloodBags, String location, String hospital, String contactNo, String status) {
        this.requestId = requestId;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.bloodBags = bloodBags;
        this.location = location;
        this.hospital = hospital;
        this.contactNo = contactNo;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public int getBloodBags() {
        return bloodBags;
    }

    public String getLocation() {
        return location;
    }

    public String getHospital() {
        return hospital;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
