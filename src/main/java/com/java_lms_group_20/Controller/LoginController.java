package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Controller.Admin.AdminDashboardController;
import com.java_lms_group_20.Controller.Student.StudentDashboardController; // Added Import
import com.java_lms_group_20.Controller.RoleSelectionController; // Added Import
import com.java_lms_group_20.Model.Role;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private Button loginButton;

    private final LoginService loginService = new LoginService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter all fields.");
            return;
        }

        try {
            User user = loginService.authenticate(username, password);

            // Navigate based on roles
            if (user.getRoles().size() > 1) {
                navigateTo("/View/role_selection.fxml", "Choose Your Role", user);
            } else if (user.getRoles().contains(Role.ADMIN)) {
                navigateTo("/View/AdminView/admin_dashboard.fxml", "Admin Dashboard", user);
            } else if (user.getRoles().contains(Role.UNDERGRADUATE)) {
                navigateTo("/View/Student/student_dashboard.fxml", "Student Portal", user);
            } else {
                messageLabel.setText("Access Denied: No valid role assigned.");
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e11d48;");
        }
    }

    private void navigateTo(String fxmlPath, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the current controller instance from the loader
            Object controller = loader.getController();

            // Pass user data BEFORE showing the stage to prevent flickering/null issues
            if (controller instanceof AdminDashboardController) {
                ((AdminDashboardController) controller).initUser(user);
            } else if (controller instanceof StudentDashboardController) {
                ((StudentDashboardController) controller).initUser(user);
            } else if (controller instanceof RoleSelectionController) {
                ((RoleSelectionController) controller).initUser(user);
            }

            // Set up and show the stage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Navigation Error: Check FXML Paths");
        }
    }
}