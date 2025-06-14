package com.example.CourseFlow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }
}
