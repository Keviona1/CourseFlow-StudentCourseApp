package com.example.CourseFlow.controller;

import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.service.AdminService;
import com.example.CourseFlow.service.CourseService;
import com.example.CourseFlow.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("totalStudents", studentService.countStudents());
        model.addAttribute("totalCourses", courseService.countCourses());
        model.addAttribute("totalEnrollments", 0L);
        model.addAttribute("totalSchedules", 0L);

        List<Student> recentStudents = studentService.getRecentStudents(5);
        model.addAttribute("recentStudents", recentStudents);
        model.addAttribute("popularCourses", List.of());
        model.addAttribute("adminUsername", authentication.getName());

        return "admin/dashboard";
    }


    @GetMapping("/students")
    public String manageStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "admin/students";
    }

    @GetMapping("/students/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "admin/add-student";
    }

    @PostMapping("/students/add")
    public String addStudent(@Valid @ModelAttribute Student student,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/add-student";
        }
        if (studentService.getStudentByEmail(student.getEmail()).isPresent()) {
            result.rejectValue("email", "error.student", "Email already exists");
            return "admin/add-student";
        }
        studentService.createStudent(student);
        redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            return "redirect:/admin/students";
        }
        model.addAttribute("student", student.get());
        return "admin/edit-student";
    }

    @PostMapping("/students/edit")
    public String editStudent(@Valid @ModelAttribute Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/edit-student";
        }
        studentService.updateStudent(student);
        redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        return "redirect:/admin/students";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/admin/students";
    }

    // Course methods...
    @GetMapping("/courses")
    public String manageCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }

    @GetMapping("/courses/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/add-course";
    }

    @PostMapping("/courses/add")
    public String addCourse(@Valid @ModelAttribute Course course,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/add-course";
        }
        courseService.createCourse(course);
        redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return "redirect:/admin/courses";
        }
        model.addAttribute("course", course.get());
        return "admin/edit-course";
    }

    @PostMapping("/courses/edit")
    public String editCourse(@Valid @ModelAttribute Course course,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/edit-course";
        }
        courseService.updateCourse(course);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/{id}/enrollments")
    public String viewCourseEnrollments(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isEmpty()) {
            return "redirect:/admin/courses";
        }
        model.addAttribute("course", course.get());
        model.addAttribute("enrollments", courseService.getCourseEnrollments(id));
        model.addAttribute("waitingList", courseService.getCourseWaitingList(id));
        return "admin/course-enrollments";
    }
}