package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTopicContainingIgnoreCase(String topic);
    long count();
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.enrollments e LEFT JOIN FETCH e.student WHERE c.id = :id")
    Optional<Course> findByIdWithEnrollments(@Param("id") Long id);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.waitingLists w LEFT JOIN FETCH w.student WHERE c.id = :id")
    Optional<Course> findByIdWithWaitingList(@Param("id") Long id);
}
