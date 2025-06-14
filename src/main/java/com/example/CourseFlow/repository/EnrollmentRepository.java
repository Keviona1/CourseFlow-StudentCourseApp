package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId AND e.isActive = true")
    Optional<Enrollment> findActiveEnrollment(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId AND e.isActive = true")
    List<Enrollment> findActiveByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.isActive = true")
    List<Enrollment> findActiveByStudentId(@Param("studentId") Long studentId);
    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student JOIN FETCH e.course")
    List<Enrollment> findAllWithStudentAndCourse();
}
