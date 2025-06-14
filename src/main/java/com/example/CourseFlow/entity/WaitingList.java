package com.example.CourseFlow.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "waiting_lists")
public class WaitingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @Column(nullable = false)
    private Integer position;

    public WaitingList() {}

    public WaitingList(Student student, Course course, Integer position) {
        this.student = student;
        this.course = course;
        this.position = position;
        this.addedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}