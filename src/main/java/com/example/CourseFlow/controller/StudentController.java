package com.example.CourseFlow.controller;

import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.entity.Schedule;
import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.service.CourseService;
import com.example.CourseFlow.service.ScheduleService;
import com.example.CourseFlow.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/login")
    public String loginForm() {
        return "student/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentByEmail(email);

        if (student.isPresent() && student.get().getPassword().equals(password)) {
            redirectAttributes.addAttribute("studentId", student.get().getId());
            return "redirect:/student/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/student/login";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Student student,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student/register";
        }

        if (studentService.getStudentByEmail(student.getEmail()).isPresent()) {
            result.rejectValue("email", "error.student", "Email already exists");
            return "student/register";
        }

        studentService.createStudent(student);
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/student/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long studentId, Model model) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isEmpty()) {
            return "redirect:/student/login";
        }

        model.addAttribute("student", student.get());
        model.addAttribute("enrollments", studentService.getStudentEnrollments(studentId));
        model.addAttribute("schedules", studentService.getStudentSchedule(studentId));
        return "student/dashboard";
    }

    @GetMapping("/courses")
    public String viewCourses(@RequestParam Long studentId,
                              @RequestParam(required = false) String topic,
                              Model model) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isEmpty()) {
            return "redirect:/student/login";
        }

        List<Course> courses;
        if (topic != null && !topic.trim().isEmpty()) {
            courses = courseService.getCoursesByTopic(topic);
        } else {
            courses = courseService.getAllCourses();
        }

        model.addAttribute("student", student.get());
        model.addAttribute("courses", courses);
        model.addAttribute("topic", topic);
        return "student/courses";
    }

    @PostMapping("/enroll")
    public String enrollInCourse(@RequestParam Long studentId,
                                 @RequestParam Long courseId,
                                 RedirectAttributes redirectAttributes) {
        String result = studentService.enrollInCourse(studentId, courseId);
        redirectAttributes.addFlashAttribute("message", result);
        redirectAttributes.addAttribute("studentId", studentId);
        return "redirect:/student/courses";
    }

    @PostMapping("/unenroll")
    public String unenrollFromCourse(@RequestParam Long studentId,
                                     @RequestParam Long courseId,
                                     RedirectAttributes redirectAttributes) {
        String result = studentService.unenrollFromCourse(studentId, courseId);
        redirectAttributes.addFlashAttribute("message", result);
        redirectAttributes.addAttribute("studentId", studentId);
        return "redirect:/student/dashboard";
    }

    @GetMapping("/schedule")
    public String viewSchedule(@RequestParam Long studentId, Model model) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isEmpty()) {
            return "redirect:/student/login";
        }

        model.addAttribute("student", student.get());
        model.addAttribute("schedules", scheduleService.getSchedulesByStudentId(studentId));
        return "student/schedule";
    }

    @GetMapping("/schedule/add")
    public String addScheduleForm(@RequestParam Long studentId, Model model) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isEmpty()) {
            return "redirect:/student/login";
        }

        Schedule schedule = new Schedule();
        schedule.setStudent(student.get());

        model.addAttribute("student", student.get());
        model.addAttribute("schedule", schedule);
        return "student/add-schedule";
    }

    @PostMapping("/schedule/add")
    public String addSchedule(@RequestParam Long studentId,
                              @Valid @ModelAttribute Schedule schedule,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student/add-schedule";
        }

        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isEmpty()) {
            return "redirect:/student/login";
        }

        schedule.setStudent(student.get());
        scheduleService.createSchedule(schedule);

        redirectAttributes.addFlashAttribute("success", "Schedule added successfully!");
        redirectAttributes.addAttribute("studentId", studentId);
        return "redirect:/student/schedule";
    }

    @PostMapping("/schedule/delete")
    public String deleteSchedule(@RequestParam Long studentId,
                                 @RequestParam Long scheduleId,
                                 RedirectAttributes redirectAttributes) {
        scheduleService.deleteSchedule(scheduleId);
        redirectAttributes.addFlashAttribute("success", "Schedule deleted successfully!");
        redirectAttributes.addAttribute("studentId", studentId);
        return "redirect:/student/schedule";
    }
}