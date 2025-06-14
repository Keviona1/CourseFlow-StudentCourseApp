package com.example.CourseFlow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Instructor is required")
    @Column(nullable = false)
    private String instructor;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(nullable = false)
    private LocalTime endTime;

    @Positive(message = "Capacity must be positive")
    @Column(nullable = false)
    private Integer capacity = 30;

    @Column(nullable = false)
    private String topic = "General";

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WaitingList> waitingLists = new ArrayList<>();

    public Course() {}

    public Course(String title, String description, String instructor, LocalTime startTime, LocalTime endTime, Integer capacity, String topic) {
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.topic = topic;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    public List<WaitingList> getWaitingLists() { return waitingLists; }
    public void setWaitingLists(List<WaitingList> waitingLists) { this.waitingLists = waitingLists; }

    public long getCurrentEnrollmentCount() {
        return enrollments.stream().filter(Enrollment::isActive).count();
    }

    public boolean hasAvailableSpots() {
        return getCurrentEnrollmentCount() < capacity;
    }
}