package com.example.CourseFlow.Service;

import com.example.CourseFlow.REPO.EnrollmentRepository;
import com.example.CourseFlow.entity.Enrollment;

import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentREPO;

    public EnrollmentService() {
        this.enrollmentREPO = new EnrollmentRepository();
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollmentREPO.save(enrollment);
    }
    //Prevent duplicate enrollments ??


    public List<Enrollment> getAllEnrollments() {return enrollmentREPO.findAll();
    }


    public void deleteEnrollmentById(long id) {
        enrollmentREPO.deleteById(id);
    }
}
