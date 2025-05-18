package com.example.CourseFlow.service;

import com.example.CourseFlow.repository.EnrollmentRepository;
import com.example.CourseFlow.entity.Enrollment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    public EnrollmentService(){}
    public void deleteEnrollment(Long Id){
        enrollmentRepository.deleteById(Id);
    }
    public Enrollment getEnrollmentById(Long id){
        return enrollmentRepository.findById(id).orElse(null);
    }
}
