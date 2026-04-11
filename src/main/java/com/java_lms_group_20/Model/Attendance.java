package com.java_lms_group_20.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Attendance {
    private int attendanceID; // Added PK
    private String undergraduateId;
    private String courseCode;
    private String sessionType; // Added (Theory/Practical)
    private Date sessionDate;
    private String status;
    private Timestamp markedAt; // Added to match SQL structure

    public Attendance() {}

    // Full constructor
    public Attendance(int attendanceID, String undergraduateId, String courseCode, String sessionType, Date sessionDate, String status) {
        this.attendanceID = attendanceID;
        this.undergraduateId = undergraduateId;
        this.courseCode = courseCode;
        this.sessionType = sessionType;
        this.sessionDate = sessionDate;
        this.status = status;
    }

    // Getters and Setters
    public int getAttendanceID() { return attendanceID; }
    public void setAttendanceID(int attendanceID) { this.attendanceID = attendanceID; }

    public String getUndergraduateId() { return undergraduateId; }
    public void setUndergraduateId(String undergraduateId) { this.undergraduateId = undergraduateId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getMarkedAt() { return markedAt; }
    public void setMarkedAt(Timestamp markedAt) { this.markedAt = markedAt; }
}