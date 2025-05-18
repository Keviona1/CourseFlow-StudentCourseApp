package com.example.CourseFlow.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Enrollment")
@Data
@RequiredArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime enrolledAt;
    //se kup tamam iden e ksaj ca variabli esht po ja futa LocalDateTime sa me qen per me e diskutu m von
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
private boolean isMember;
//per kto t dyja t duhen te bera ato t parat me t ik erroret
public Enrollment(Student student, Course course, LocalDateTime enrolledAt, boolean isMember) {
    this.student = student;
    this.course = course;
    this.enrolledAt = enrolledAt;
    this.isMember = isMember;
 }
}
