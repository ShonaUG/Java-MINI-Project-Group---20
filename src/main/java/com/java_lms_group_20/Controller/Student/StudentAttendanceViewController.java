package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.AttendanceSummary;
import com.java_lms_group_20.Service.AttendanceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;

public class StudentAttendanceViewController {
    @FXML private TableView<AttendanceSummary> tblAttendance;
    @FXML private TableColumn<AttendanceSummary, String> colCourse, colPercentage, colStatus;
    @FXML private TableColumn<AttendanceSummary, Integer> colTotal, colPresent;

    private final AttendanceService service = new AttendanceService();

    @FXML
    public void initialize() {
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        colPresent.setCellValueFactory(new PropertyValueFactory<>("presentCount"));
        colPercentage.setCellValueFactory(new PropertyValueFactory<>("percentageText"));

        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    double pct = ((AttendanceSummary) getTableRow().getItem()).getPercentage();
                    if (pct >= 80) {
                        setText("✅ ELIGIBLE");
                        setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                    } else {
                        setText("❌ INELIGIBLE");
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    public void loadAttendance(String studentID) {
        try {
            List<AttendanceSummary> summary = service.getStudentAttendanceSummary(studentID);
            tblAttendance.setItems(FXCollections.observableArrayList(summary));
        } catch (SQLException e) { e.printStackTrace(); }
    }
}