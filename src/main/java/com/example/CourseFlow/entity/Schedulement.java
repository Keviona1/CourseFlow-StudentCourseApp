package com.example.CourseFlow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Schedulement")
@Data
@RequiredArgsConstructor
public class Schedulement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
   @OneToOne
   private Student student;
   @OneToOne
    private Course course;
   private LocalDateTime start_time;
   private LocalDateTime end_time;

    public Schedulement(String title, String description,
                        Student student, Course course, LocalDateTime start_time,
                        LocalDateTime end_time)
    {
        this.title = title;
        this.description = description;
        this.student = student;
        this.course = course;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
