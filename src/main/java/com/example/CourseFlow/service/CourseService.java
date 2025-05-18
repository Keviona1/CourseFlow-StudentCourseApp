package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Course;
import com.example.CourseFlow.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseService() {}

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }


    public void saveCourse(Course course) {
        courseRepository.save(course);
    }


    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }


}
