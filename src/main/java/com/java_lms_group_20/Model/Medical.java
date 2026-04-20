package com.java_lms_group_20.Model;

import java.time.LocalDate;
import java.sql.Date;

public class Medical {
    private int medicalID;
    private String undergraduateId;
    private String description;
    private LocalDate validFrom; // Keep this internal for better date math
    private LocalDate validTo;
    private String status;

    public Medical() {}

    // --- Standard Getters & Setters ---

    public int getMedicalID() { return medicalID; }
    public void setMedicalID(int medicalID) { this.medicalID = medicalID; }

    public String getUndergraduateId() { return undergraduateId; }
    public void setUndergraduateId(String undergraduateId) { this.undergraduateId = undergraduateId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // --- SQL DATE COMPATIBLE GETTERS ---

    /**
     * This allows your Repository and Controller to see java.sql.Date
     */
    public Date getValidFrom() {
        return (validFrom != null) ? Date.valueOf(validFrom) : null;
    }

    public Date getValidTo() {
        return (validTo != null) ? Date.valueOf(validTo) : null;
    }

    // --- SQL DATE COMPATIBLE SETTERS ---

    public void setValidFrom(Date sqlDate) {
        if (sqlDate != null) {
            this.validFrom = sqlDate.toLocalDate();
        }
    }

    public void setValidTo(Date sqlDate) {
        if (sqlDate != null) {
            this.validTo = sqlDate.toLocalDate();
        }
    }

    // --- INTERNAL LOCALDATE METHODS (Optional) ---
    // Useful if you need to do logic check like: if(medical.getValidFromDate().isBefore(now))

    public LocalDate getValidFromDate() { return validFrom; }
    public void setValidFromDate(LocalDate ld) { this.validFrom = ld; }
}