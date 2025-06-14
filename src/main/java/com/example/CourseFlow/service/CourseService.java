package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.entity.Enrollment;
import com.example.CourseFlow.entity.WaitingList;
import com.example.CourseFlow.repository.CourseRepository;
import com.example.CourseFlow.repository.EnrollmentRepository;
import com.example.CourseFlow.repository.WaitingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private WaitingListRepository waitingListRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByTopic(String topic) {
        return courseRepository.findByTopicContainingIgnoreCase(topic);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
    public long countCourses() {
        return courseRepository.count();
    }
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Enrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findActiveByCourseId(courseId);
    }

    public List<WaitingList> getCourseWaitingList(Long courseId) {
        return waitingListRepository.findByCourseIdOrderByPosition(courseId);
    }
}
