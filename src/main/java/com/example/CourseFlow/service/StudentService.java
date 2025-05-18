package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.repository.StudentRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    // Get a student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
    // Save or update a student (hashes password before saving)
    public void saveStudent(Student student) {
        // Hash the password before saving
//        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
    }
    // Delete a student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    // Find by email for login
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
