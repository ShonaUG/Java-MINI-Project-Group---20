package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Service.AdminService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminStudentViewController {
    @FXML private TableView<Undergraduate> studentTable;
    @FXML private TableColumn<Undergraduate, String> colStuID, colName, colDegree;
    @FXML private TableColumn<Undergraduate, Double> colGPA;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colDegree.setCellValueFactory(new PropertyValueFactory<>("degreeProgram"));
        colGPA.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        studentTable.setItems(FXCollections.observableArrayList(adminService.getStudentList()));
    }
}