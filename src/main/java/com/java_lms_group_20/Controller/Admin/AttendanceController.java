package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Service.AttendanceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;

public class AttendanceController {
    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colStuID, colCourse, colType, colStatus;
    @FXML private TableColumn<Attendance, java.sql.Date> colDate;
    @FXML private TextField txtSearchID;
    @FXML private Label lblStatus;

    private final AttendanceService service = new AttendanceService();

    @FXML
    public void initialize() {
        // MATCH THESE STRINGS EXACTLY TO THE ATTENDANCE CLASS VARIABLES
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            String searchId = (txtSearchID.getText() == null) ? "" : txtSearchID.getText().trim();
            List<Attendance> results = service.getRawAttendanceByStudentId(searchId);

            if (results.isEmpty()) {
                lblStatus.setText("No records found for: " + searchId);
            } else {
                lblStatus.setText("Found " + results.size() + " records.");
            }

            attendanceTable.setItems(FXCollections.observableArrayList(results));
        } catch (SQLException e) {
            lblStatus.setText("Database error!");
            e.printStackTrace();
        }
    }

    @FXML private void markPresent() { updateStatus("Present", "#10b981"); }
    @FXML private void markAbsent() { updateStatus("Absent", "#ef4444"); }

    private void updateStatus(String status, String color) {
        Attendance selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                if (service.changeAttendanceStatus(selected.getAttendanceID(), status)) {
                    lblStatus.setText("Updated: " + selected.getUndergraduateId());
                    lblStatus.setStyle("-fx-text-fill: " + color);
                    handleSearch();
                }
            } catch (SQLException e) {
                lblStatus.setText("Update Failed!");
            }
        }
    }
}