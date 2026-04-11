package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final CourseRepository repository = new CourseRepository();

    public List<Course> getStudentCourses(String studentID) {
        if (studentID == null || studentID.trim().isEmpty()) {
            return new ArrayList<>();
        }
        // Updated to return the full Course object list
        return repository.getCoursesByStudentID(studentID.trim());
    }

    public void registerCourse(Course course) throws Exception {
        if (!repository.save(course)) throw new Exception("Course registration failed.");
    }

    public void updateCourse(Course course) throws Exception {
        if (!repository.update(course)) throw new Exception("Course update failed.");
    }

    public void deleteCourse(String courseCode) throws Exception {
        if (!repository.delete(courseCode)) throw new Exception("Course deletion failed.");
    }
}