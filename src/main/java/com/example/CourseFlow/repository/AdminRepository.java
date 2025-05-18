package com.example.CourseFlow.repository;

import com.example.CourseFlow.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);
    Admin findByEmail(String email);
}
