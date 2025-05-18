package com.example.CourseFlow.entity;

import jakarta.persistence.*;

@Entity
public class Enrollment {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Or FetchType.EAGER, depending on your needs
    @JoinColumn(name = "student_id", nullable = false) // Defines the foreign key column in the Enrollment table
    private Student student;

}
