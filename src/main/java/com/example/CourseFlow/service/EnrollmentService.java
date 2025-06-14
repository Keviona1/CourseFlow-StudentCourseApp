package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.entity.Enrollment;
import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.repository.EnrollmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enrollStudent(Optional<Student> studentOpt, Course course) {
        Student student = studentOpt.orElseThrow(() -> {
            logger.error("Attempted to enroll a non-existent student");
            return new IllegalArgumentException("Student not found");
        });

        if (course == null || course.getId() == null) {
            logger.error("Attempted to enroll with invalid course");
            throw new IllegalArgumentException("Course cannot be null");
        }

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findActiveEnrollment(student.getId(), course.getId());
        if (existingEnrollment.isPresent()) {
            logger.warn("Student {} already enrolled in course {}", student.getId(), course.getId());
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setActive(true); // Ensure enrollment is active
        enrollmentRepository.save(enrollment);
        logger.info("Student {} enrolled in course {}", student.getId(), course.getId());
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findActiveByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findActiveByCourseId(courseId);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
        logger.info("Enrollment {} deleted", id);
    }
}