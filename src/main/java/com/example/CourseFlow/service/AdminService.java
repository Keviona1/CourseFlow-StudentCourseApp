package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Admin;
import com.example.CourseFlow.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public boolean authenticate(String username, String password) {
        Optional<Admin> admin = getAdminByUsername(username);
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }
}
