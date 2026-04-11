package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Lecturer;
import com.java_lms_group_20.Service.AdminService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminLecturerViewController {
    @FXML private TableView<Lecturer> lecturerTable;
    @FXML private TableColumn<Lecturer, String> colName, colEmail, colDepartment, colSpecialization;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        lecturerTable.setItems(FXCollections.observableArrayList(adminService.getLecturerList()));
    }
}