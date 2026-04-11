package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Service.AdminService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminCourseViewController {
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> colCode, colCourseName;
    @FXML private TableColumn<Course, Integer> colCredits;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));

        courseTable.setItems(FXCollections.observableArrayList(adminService.getCourseList()));
    }
}