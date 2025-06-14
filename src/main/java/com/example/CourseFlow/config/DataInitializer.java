package com.example.CourseFlow.config;

import com.example.CourseFlow.entity.Admin;
import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.service.AdminService;
import com.example.CourseFlow.service.CourseService;
import com.example.CourseFlow.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            if (adminService.getAdminByUsername("admin").isEmpty()) {
                Admin admin = new Admin("admin@example.com", "admin", passwordEncoder.encode("admin123"), "Admin");
                adminService.createAdmin(admin);
                logger.info("Default admin created with username: admin, password: admin123");
            } else {
                logger.info("Admin already exists, skipping creation");
            }

            try {
                if (studentService.getAllStudents() == null || studentService.getAllStudents().isEmpty()) {
                    Student student1 = new Student("John Doe", "john@example.com", passwordEncoder.encode("password123"));
                    Student student2 = new Student("Jane Smith", "jane@example.com", passwordEncoder.encode("password123"));
                    studentService.createStudent(student1);
                    studentService.createStudent(student2);
                    logger.info("Sample students created");
                } else {
                    logger.info("Students already exist, skipping creation");
                }
            } catch (Exception e) {
                logger.warn("Could not create students: " + e.getMessage());
            }

            try {
                if (courseService.getAllCourses() == null || courseService.getAllCourses().isEmpty()) {
                    Course course1 = new Course("Java Programming", "Learn Java from basics to advanced",
                            "Dr. Smith", LocalTime.of(9, 0), LocalTime.of(11, 0), 25, "Programming");
                    Course course2 = new Course("Database Systems", "Comprehensive database management course",
                            "Prof. Johnson", LocalTime.of(14, 0), LocalTime.of(16, 0), 30, "Database");
                    Course course3 = new Course("Web Development", "Modern web development with Spring Boot",
                            "Dr. Wilson", LocalTime.of(10, 0), LocalTime.of(12, 0), 20, "Web");

                    courseService.createCourse(course1);
                    courseService.createCourse(course2);
                    courseService.createCourse(course3);
                    logger.info("Sample courses created");
                } else {
                    logger.info("Courses already exist, skipping creation");
                }
            } catch (Exception e) {
                logger.warn("Could not create courses: " + e.getMessage());
            }

        } catch (Exception e) {
            logger.error("Error during data initialization", e);
        }
    }
}