package com.java_lms_group_20.Repository;

import com.java_lms_group_20.Model.Role;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Util.DBConnection;

import java.sql.*;
import java.util.Optional;

public class LoginRepository {

    public Optional<User> findByUsername(String identifier) {

        String query = "SELECT u.*, r.roleName, ug.studentID " +
                "FROM user u " +
                "LEFT JOIN user_roles ur ON u.userID = ur.userID " +
                "LEFT JOIN role r ON ur.roleID = r.roleID " +
                "LEFT JOIN undergraduate ug ON u.userID = ug.userID " +
                "WHERE u.username = ? OR ug.studentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, identifier);
            pstmt.setString(2, identifier); // Check against TG number too
            ResultSet rs = pstmt.executeQuery();

            User user = null;
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setUserID(rs.getInt("userID"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                }

                String roleName = rs.getString("roleName");
                if (roleName != null) {
                    user.addRole(Role.valueOf(roleName));
                }
            }
            return Optional.ofNullable(user);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}