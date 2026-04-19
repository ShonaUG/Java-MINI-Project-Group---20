package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Controller.LoginController;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Util.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LecturerDashboardController {

    @FXML private Label lblWelcome, lblLecturerID;
    @FXML private StackPane contentArea;
    @FXML private Button btnHome, btnAttendance, btnMarks, btnMedical;

    private User currentUser;
    private String lecturerID;

    public void initUser(User user) {
        this.currentUser = user;
        this.lecturerID = fetchLecturerID(user.getUserID());

        Platform.runLater(() -> {
            lblWelcome.setText("Welcome, " + user.getFirstName());
            lblLecturerID.setText(lecturerID != null ? lecturerID : "Lec ID: N/A");
        });

        // Load the default home view
        showHome();
    }

    private String fetchLecturerID(int userID) {
        String sql = "SELECT lecturerID FROM lecturer WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("lecturerID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML private void showHome() { switchView("/View/Lecturer/lecturer_home.fxml"); }
    @FXML private void showAttendance() { switchView("/View/Lecturer/mark_attendance.fxml"); }
    @FXML private void showMarks() { switchView("/View/Lecturer/manage_marks.fxml"); }
    @FXML private void showMedical() { switchView("/View/Lecturer/view_medicals.fxml"); }

    @FXML
    private void handleSignOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            LoginController.logout(lblWelcome);
        }
    }

    private void switchView(String fxmlPath) {
        try {
            java.net.URL res = getClass().getResource(fxmlPath);
            if (res == null) {
                System.err.println("Resource not found: " + fxmlPath);
                return; // Stop execution without crashing
            }
            FXMLLoader loader = new FXMLLoader(res);
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error loading: " + fxmlPath);
            e.printStackTrace();
        }
    }
}