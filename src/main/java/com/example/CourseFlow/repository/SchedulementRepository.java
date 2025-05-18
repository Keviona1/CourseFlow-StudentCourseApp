package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Enrollment;
import com.example.CourseFlow.entity.Schedulement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulementRepository extends JpaRepository<Schedulement, Long> {
    List<Schedulement> findAvailableHours(LocalDateTime availableHours);

}
