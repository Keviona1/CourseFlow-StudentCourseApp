package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.*;
import com.example.CourseFlow.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; // <-- 1. THIS IS THE CORRECT IMPORT
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private WaitingListRepository waitingListRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getRecentStudents(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return studentRepository.findByOrderByIdDesc(pageable);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public long countStudents() {
        return studentRepository.count();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean hasScheduleConflict(Long studentId, LocalTime courseStart, LocalTime courseEnd) {

        LocalDateTime startTime = LocalDateTime.now().with(courseStart);
        LocalDateTime endTime = LocalDateTime.now().with(courseEnd);

        List<Schedule> conflicts = scheduleRepository.findConflictingSchedules(studentId, startTime, endTime);
        return !conflicts.isEmpty();
    }

    public String enrollInCourse(Long studentId, Long courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return "Student or Course not found";
        }

        Student student = studentOpt.get();
        Course course = courseOpt.get();

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findActiveEnrollment(studentId, courseId);
        if (existingEnrollment.isPresent()) {
            return "Already enrolled in this course";
        }

        if (hasScheduleConflict(studentId, course.getStartTime(), course.getEndTime())) {
            return "Schedule conflict detected";
        }

        if (course.hasAvailableSpots()) {
            Enrollment enrollment = new Enrollment(student, course);
            enrollmentRepository.save(enrollment);
            return "Successfully enrolled";
        } else {
            List<WaitingList> waitingList = waitingListRepository.findByCourseIdOrderByPosition(courseId);
            int position = waitingList.size() + 1;

            WaitingList waiting = new WaitingList(student, course, position);
            waitingListRepository.save(waiting);
            return "Added to waiting list at position " + position;
        }
    }

    public String unenrollFromCourse(Long studentId, Long courseId) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findActiveEnrollment(studentId, courseId);

        if (enrollmentOpt.isEmpty()) {
            return "Not enrolled in this course";
        }

        Enrollment enrollment = enrollmentOpt.get();
        enrollment.setActive(false);
        enrollmentRepository.save(enrollment);

        processWaitingList(courseId);

        return "Successfully unenrolled";
    }

    private void processWaitingList(Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) return;

        Course course = courseOpt.get();
        if (!course.hasAvailableSpots()) return;

        List<WaitingList> waitingList = waitingListRepository.findByCourseIdOrderByPosition(courseId);
        if (!waitingList.isEmpty()) {
            WaitingList firstInLine = waitingList.get(0);

            Enrollment newEnrollment = new Enrollment(firstInLine.getStudent(), course);
            enrollmentRepository.save(newEnrollment);

            waitingListRepository.delete(firstInLine);

            for (int i = 1; i < waitingList.size(); i++) {
                WaitingList waiting = waitingList.get(i);
                waiting.setPosition(i);
                waitingListRepository.save(waiting);
            }
        }
    }

    public List<Enrollment> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findActiveByStudentId(studentId);
    }

    public List<Schedule> getStudentSchedule(Long studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }
}