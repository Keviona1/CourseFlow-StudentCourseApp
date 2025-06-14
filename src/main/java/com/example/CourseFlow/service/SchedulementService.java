package com.example.CourseFlow.service;

import com.example.CourseFlow.entity.Schedulement;
import com.example.CourseFlow.repository.SchedulementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulementService {

    @Autowired
    private SchedulementRepository schedulementRepository;

    public List<Schedulement> getAllSchedulements() {
        return schedulementRepository.findAll();
    }

    public void saveSchedulement(Schedulement schedulement) {
        schedulementRepository.save(schedulement);
    }

    public Optional<Schedulement> getSchedulementById(Long id) {
        return schedulementRepository.findById(id);
    }

    public void deleteSchedulement(Long id) {
        schedulementRepository.deleteById(id);
    }
}
