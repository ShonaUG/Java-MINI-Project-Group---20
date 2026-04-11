package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    /**
     * Fetches Course objects containing both Code and Name by joining
     * the attendance table with the course library table.
     */
    public List<Course> getCoursesByStudentID(String studentID) {
        List<Course> courses = new ArrayList<>();

        // We JOIN attendance (a) with course (c) to get the name for the code.
        String query = "SELECT DISTINCT a.courseCode, c.courseName " +
                "FROM attendance a " +
                "LEFT JOIN course c ON a.courseCode = c.courseCode " +
                "WHERE a.undergraduateId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setCourseCode(rs.getString("courseCode"));

                String name = rs.getString("courseName");
                // If the code exists in attendance but not in the course table:
                course.setCourseName(name != null ? name : "Module Name Pending");

                courses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("DB ERROR in CourseRepository: " + e.getMessage());
        }
        return courses;
    }

    public boolean save(Course course) throws SQLException {
        String sql = "INSERT INTO course (courseCode, courseName, credits) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Course course) throws SQLException {
        String sql = "UPDATE course SET courseName = ?, credits = ? WHERE courseCode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getCredits());
            stmt.setString(3, course.getCourseCode());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(String courseCode) throws SQLException {
        String sql = "DELETE FROM course WHERE courseCode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            return stmt.executeUpdate() > 0;
        }
    }
}