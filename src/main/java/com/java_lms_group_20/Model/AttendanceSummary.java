package com.java_lms_group_20.Model;

public class AttendanceSummary {
    private String courseCode;
    private int totalSessions;
    private int presentCount;
    private double percentage;

    public AttendanceSummary(String courseCode, int totalSessions, int presentCount) {
        this.courseCode = courseCode;
        this.totalSessions = totalSessions;
        this.presentCount = presentCount;
        this.percentage = totalSessions > 0 ? (double) presentCount / totalSessions * 100 : 0;
    }

    public String getCourseCode() { return courseCode; }
    public int getTotalSessions() { return totalSessions; }
    public int getPresentCount() { return presentCount; }
    public double getPercentage() { return percentage; }
    public String getPercentageText() { return String.format("%.1f%%", percentage); }
}