package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Student;
import org.springframework.data.domain.Pageable; // <-- 1. THIS IS THE CORRECT IMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    long count();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course WHERE s.id = :id")
    Optional<Student> findByIdWithEnrollments(@Param("id") Long id);

    List<Student> findByOrderByIdDesc(Pageable pageable);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.schedule WHERE s.id = :id")
    Optional<Student> findByIdWithSchedules(Long id);
}