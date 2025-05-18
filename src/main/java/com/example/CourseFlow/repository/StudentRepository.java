package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    Student findByEmail(String email);


}
