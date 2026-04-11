package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Service.CourseService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.util.List;

public class CourseViewController {
    @FXML private FlowPane courseContainer;
    private final CourseService courseService = new CourseService();

    public void loadCourses(String studentID) {
        if (studentID == null || studentID.isEmpty()) return;

        new Thread(() -> {
            try {
                // Fetch full Course objects
                List<Course> courses = courseService.getStudentCourses(studentID);

                Platform.runLater(() -> {
                    if (courseContainer == null) return;
                    courseContainer.getChildren().clear();

                    if (courses.isEmpty()) {
                        showEmptyMessage(studentID);
                    } else {
                        String[] colors = {"#4f46e5", "#0ea5e9", "#10b981", "#f59e0b"};
                        for (int i = 0; i < courses.size(); i++) {
                            courseContainer.getChildren().add(createCourseCard(courses.get(i), colors[i % colors.length]));
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showEmptyMessage(String studentID) {
        Label noData = new Label("No courses found for: " + studentID);
        noData.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px; -fx-font-style: italic;");
        courseContainer.getChildren().add(noData);
    }

    private VBox createCourseCard(Course course, String color) {
        VBox card = new VBox(5);
        card.setPrefSize(260, 120); // Slightly wider for full names
        card.setStyle("-fx-background-color: " + color + "; " +
                "-fx-background-radius: 12; -fx-padding: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 4);");

        // Course Code (Small top label)
        Label codeText = new Label(course.getCourseCode());
        codeText.setStyle("-fx-text-fill: white; -fx-opacity: 0.7; -fx-font-size: 10px; -fx-font-weight: bold;");

        // Course Name (Main large label)
        Label nameText = new Label(course.getCourseName());
        nameText.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        nameText.setWrapText(true);

        card.getChildren().addAll(codeText, nameText);
        return card;
    }
}