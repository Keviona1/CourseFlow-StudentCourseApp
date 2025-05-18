package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Admin;
import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.repository.AdminRepository;
import com.example.CourseFlow.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    // Get all admin
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    // Save or update admin (hashes password before saving)
    public void saveAdmin(Admin admin) {
        // Hash the password before saving
//        student.setPassword(passwordEncoder.encode(student.getPassword()));
        adminRepository.save(admin);
    }

    // Delete a admin
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);

    }
}
    // Find by email for login

