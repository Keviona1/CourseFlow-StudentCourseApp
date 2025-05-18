package com.example.CourseFlow.REPO;

import com.example.CourseFlow.entity.Enrollment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollmentRepository {
    public void save(Enrollment enrollment) {
        Transaction transaction = null;
        List<LocalDateTime>Lt = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(enrollment);
            Lt.add(enrollment.getEnrolledAt());
            transaction.commit();
        } catch (Exception e) {
            for(LocalDateTime lt : Lt){
                if (enrollment.getEnrolledAt() == lt){
                    transaction.rollback();
                    e.printStackTrace();
                    break;
                }
                else
                    Lt.add(enrollment.getEnrolledAt());

            }
        }
    }
    private SessionFactory sessionFactory;

    public EnrollmentRepository() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Enrollment> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Enrollment", Enrollment.class).list();
        }
    }
    public void deleteById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Enrollment enrollment = session.get(Enrollment.class, id);
            if (enrollment != null) {
                session.delete(enrollment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
