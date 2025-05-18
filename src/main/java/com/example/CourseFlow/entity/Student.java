package com.example.CourseFlow.entity;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedulement schedule_Id;

    public Student() {
    }

    public Student(String name, String email, String password, Schedulement schedule_Id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.schedule_Id = schedule_Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Schedulement getSchedule_Id() {
        return schedule_Id;
    }

    public void setSchedule_Id(Schedulement schedule_Id) {
        this.schedule_Id = schedule_Id;
    }
}
