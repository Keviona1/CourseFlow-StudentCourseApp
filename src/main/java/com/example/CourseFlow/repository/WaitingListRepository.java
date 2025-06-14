package com.example.CourseFlow.repository;


import com.example.CourseFlow.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {
    @Query("SELECT w FROM WaitingList w WHERE w.course.id = :courseId ORDER BY w.position ASC")
    List<WaitingList> findByCourseIdOrderByPosition(@Param("courseId") Long courseId);

    @Query("SELECT w FROM WaitingList w WHERE w.student.id = :studentId AND w.course.id = :courseId")
    Optional<WaitingList> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT w FROM WaitingList w WHERE w.course.id = :courseId ORDER BY w.position ASC")
    List<WaitingList> findFirstInWaitingList(@Param("courseId") Long courseId);
}