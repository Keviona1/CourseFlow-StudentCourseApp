package com.example.CourseFlow.controller;

import com.example.CourseFlow.entity.Schedulement;
import com.example.CourseFlow.service.SchedulementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedulements")
public class SchedulementController {

    @Autowired
    private SchedulementService schedulementService;

    @GetMapping
    public String listSchedulements(Model model) {
        model.addAttribute("schedulements", schedulementService.getAllSchedulements());
        return "schedulements";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("schedulement", new Schedulement());
        return "schedulement-form";
    }

    @PostMapping("/save")
    public String saveSchedulement(@ModelAttribute Schedulement schedulement) {
        schedulementService.saveSchedulement(schedulement);
        return "redirect:/schedulements";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedulement(@PathVariable Long id) {
        schedulementService.deleteSchedulement(id);
        return "redirect:/schedulements";
    }
}
