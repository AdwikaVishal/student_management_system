package com.example.student_management.repository;

import com.example.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);
    List<Student> findByNameContainingIgnoreCase(String partialName);
    boolean existsByRollNumber(Long rollNumber);
    void deleteByRollNumber(Long rollNumber);
}
