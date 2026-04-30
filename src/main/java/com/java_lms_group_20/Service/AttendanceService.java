package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Model.AttendanceSummary;
import com.java_lms_group_20.Repository.AttendanceRepository;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AttendanceService {
    private final AttendanceRepository repository = new AttendanceRepository();

    // Used by Admin
    public List<Attendance> getRawAttendanceByStudentId(String studentId) throws SQLException {
        return repository.findByStudentId(studentId == null ? "" : studentId.trim());
    }

    // Logic for Student Summary (Subject-wise %)
    public List<AttendanceSummary> getStudentAttendanceSummary(String studentId) throws SQLException {
        List<Attendance> rawData = repository.findByStudentId(studentId);

        Map<String, List<Attendance>> grouped = new HashMap<>();

        // Group by courseCode
        for (Attendance a : rawData) {
            String code = a.getCourseCode();
            grouped.computeIfAbsent(code, k -> new ArrayList<>()).add(a);
        }

        List<AttendanceSummary> summaries = new ArrayList<>();

        // Calculate totals
        for (String code : grouped.keySet()) {
            List<Attendance> sessions = grouped.get(code);

            int total = sessions.size();
            int present = 0;

            for (Attendance a : sessions) {
                if ("Present".equalsIgnoreCase(a.getStatus())) {
                    present++;
                }
            }

            summaries.add(new AttendanceSummary(code, total, present));
        }

        return summaries;
    }

    public boolean changeAttendanceStatus(int attendanceID, String status) throws SQLException {
        return repository.updateStatus(attendanceID, status);
    }

    public boolean addAttendance(Attendance attendance) throws SQLException {
        return repository.save(attendance);
    }
}
