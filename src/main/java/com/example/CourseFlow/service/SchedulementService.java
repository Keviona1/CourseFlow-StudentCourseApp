package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Enrollment;
import com.example.CourseFlow.entity.Schedulement;
import com.example.CourseFlow.repository.EnrollmentRepository;
import com.example.CourseFlow.repository.SchedulementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulementService {
    private SchedulementRepository SchRepository;
    public SchedulementService(EnrollmentRepository enrollmentRepository) {
        this.SchRepository = SchRepository;
    }
    public List<Schedulement> getAllSchedulements() {
        return SchRepository.findAll();
    }
    public SchedulementService(){}
    public Schedulement getEnrollmentById(Long id){
        return SchRepository.findById(id).orElse(null);
    }
}
