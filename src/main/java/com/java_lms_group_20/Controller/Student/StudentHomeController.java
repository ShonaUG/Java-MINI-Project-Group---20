package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.Notice;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.CourseService;
import com.java_lms_group_20.Service.NoticeService;
import com.java_lms_group_20.Service.UndergraduateService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentHomeController {

    @FXML private Label lblCurrentGpa;
    @FXML private Label lblCourseCount;
    @FXML private Label lblNoticeCount;
    @FXML private VBox noticeContainer;
    @FXML private Label lblDashboardStatus;

    private final UndergraduateService undergraduateService = new UndergraduateService();
    private final CourseService courseService = new CourseService();
    private final NoticeService noticeService = new NoticeService();

    public void initUser(User user, String studentID) {
        if (user == null || studentID == null || studentID.isBlank()) {
            lblDashboardStatus.setText("Student details are not available.");
            return;
        }

        loadSummary(user.getUserID(), studentID);
        loadNotices();
    }

    private void loadSummary(int userID, String studentID) {
        try {
            var studentOpt = undergraduateService.getByUserID(userID);
            if (studentOpt.isPresent()) {
                lblCurrentGpa.setText(String.format("%.2f", studentOpt.get().getGpa()));
            } else {
                lblCurrentGpa.setText("N/A");
            }

            int courseCount = courseService.getStudentCourses(studentID).size();
            lblCourseCount.setText(String.format("%02d", courseCount));
        } catch (Exception e) {
            lblCurrentGpa.setText("N/A");
            lblCourseCount.setText("00");
            lblDashboardStatus.setText("Failed to load dashboard summary.");
        }
    }

    private void loadNotices() {
        try {
            List<Notice> notices = noticeService.getAllNotices();
            lblNoticeCount.setText(String.format("%02d", notices.size()));
            noticeContainer.getChildren().clear();

            if (notices.isEmpty()) {
                Label emptyLabel = new Label("No notices available right now.");
                emptyLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
                noticeContainer.getChildren().add(emptyLabel);
                return;
            }

            int limit = Math.min(3, notices.size());
            for (int i = 0; i < limit; i++) {
                noticeContainer.getChildren().add(buildNoticeCard(notices.get(i)));
            }
            lblDashboardStatus.setText("Showing latest " + limit + " notice(s)");
        } catch (SQLException e) {
            lblNoticeCount.setText("00");
            noticeContainer.getChildren().clear();
            Label errorLabel = new Label("Failed to load notices.");
            errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 14px;");
            noticeContainer.getChildren().add(errorLabel);
            lblDashboardStatus.setText("Notice feed unavailable");
        }
    }

    private VBox buildNoticeCard(Notice notice) {
        VBox card = new VBox(6);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 14; "
                + "-fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label titleLabel = new Label(notice.getTitle());
        titleLabel.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 15px; -fx-font-weight: bold;");

        Label metaLabel = new Label(buildMetaText(notice));
        metaLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");

        Label messageLabel = new Label(notice.getMessage());
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-text-fill: #334155; -fx-font-size: 13px;");

        card.getChildren().addAll(titleLabel, metaLabel, messageLabel);
        return card;
    }

    private String buildMetaText(Notice notice) {
        String postedBy = notice.getPostedBy() == null || notice.getPostedBy().isBlank()
                ? "System"
                : notice.getPostedBy();

        if (notice.getPostedAt() == null) {
            return postedBy;
        }

        return postedBy + " | " + notice.getPostedAt()
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
