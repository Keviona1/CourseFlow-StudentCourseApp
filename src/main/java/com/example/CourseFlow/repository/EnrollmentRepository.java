package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Enrollment;
import com.example.CourseFlow.service.EnrollmentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {






    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
}
