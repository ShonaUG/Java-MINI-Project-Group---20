package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Service.MedicalService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;

public class StudentMedicalController {

    @FXML private Label lblStudentId;
    @FXML private Label lblStatus;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker dpValidFrom;
    @FXML private DatePicker dpValidTo;

    @FXML private TableView<Medical> medicalTable;
    @FXML private TableColumn<Medical, String> colDesc;
    @FXML private TableColumn<Medical, Date> colFrom;
    @FXML private TableColumn<Medical, Date> colTo;
    @FXML private TableColumn<Medical, String> colStatus;

    private final MedicalService medicalService = new MedicalService();
    private String studentId;

    @FXML
    public void initialize() {
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colFrom.setCellValueFactory(new PropertyValueFactory<>("validFrom"));
        colTo.setCellValueFactory(new PropertyValueFactory<>("validTo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void initStudent(String studentId) {
        this.studentId = studentId;
        lblStudentId.setText(studentId != null ? studentId : "Unavailable");
        loadMedicalRecords();
    }

    @FXML
    private void handleSubmit() {
        if (studentId == null || studentId.isBlank()) {
            setStatus("Student ID is not available.", false);
            return;
        }

        String description = txtDescription.getText() == null ? "" : txtDescription.getText().trim();
        if (description.isEmpty() || dpValidFrom.getValue() == null || dpValidTo.getValue() == null) {
            setStatus("Description, valid from, and valid to are required.", false);
            return;
        }

        if (dpValidTo.getValue().isBefore(dpValidFrom.getValue())) {
            setStatus("Valid to date cannot be earlier than valid from date.", false);
            return;
        }

        try {
            Medical medical = new Medical();
            medical.setUndergraduateId(studentId);
            medical.setDescription(description);
            medical.setValidFrom(Date.valueOf(dpValidFrom.getValue()));
            medical.setValidTo(Date.valueOf(dpValidTo.getValue()));
            medical.setStatus("Pending");

            if (medicalService.addMedical(medical)) {
                clearForm();
                loadMedicalRecords();
                setStatus("Medical request submitted.", true);
            } else {
                setStatus("Medical request could not be submitted.", false);
            }
        } catch (SQLException e) {
            setStatus("Failed to submit medical request.", false);
        }
    }

    @FXML
    private void handleRefresh() {
        loadMedicalRecords();
    }

    private void loadMedicalRecords() {
        if (studentId == null || studentId.isBlank()) {
            medicalTable.setItems(FXCollections.observableArrayList());
            return;
        }

        try {
            var records = medicalService.getMedicalsByStudentId(studentId);
            medicalTable.setItems(FXCollections.observableArrayList(records));
            if (records.isEmpty()) {
                setStatus("No medical requests found.", false);
            } else {
                setStatus("Loaded " + records.size() + " medical request(s).", true);
            }
        } catch (SQLException e) {
            setStatus("Failed to load medical requests.", false);
        }
    }

    private void clearForm() {
        txtDescription.clear();
        dpValidFrom.setValue(null);
        dpValidTo.setValue(null);
    }

    private void setStatus(String message, boolean success) {
        lblStatus.setText(message);
        lblStatus.setStyle(success ? "-fx-text-fill: #10b981;" : "-fx-text-fill: #ef4444;");
    }
}
