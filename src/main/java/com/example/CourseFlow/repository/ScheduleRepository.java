package com.example.CourseFlow.repository;


import com.example.CourseFlow.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.student.id = :studentId")
    List<Schedule> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT s FROM Schedule s WHERE s.student.id = :studentId AND " +
            "((s.startDateTime <= :endTime AND s.endDateTime >= :startTime))")
    List<Schedule> findConflictingSchedules(@Param("studentId") Long studentId,
                                            @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);
}
