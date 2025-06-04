package com.example.student_management.service;

import com.example.student_management.model.Student;
import com.example.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String addStudent(Student student) {
        if (studentRepository.existsById(student.getRollNumber())) {
            return "Student with this roll number already exists.";
        }
        studentRepository.save(student);
        return "Student added successfully.";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Student getStudentByRollNumber(Long rollNumber) {
        return studentRepository.findById(rollNumber).orElse(null);
    }

    public String updateMarks(Long rollNumber, int subjectIndex, int newMark) {
        Student student = getStudentByRollNumber(rollNumber);
        if (student == null) {
            return "Student not found.";
        }

        List<Integer> marks = student.getMarks();

        if (subjectIndex < 0 || subjectIndex >= marks.size()) {
            return "Invalid subject index.";
        }

        if (newMark < 0 || newMark > 100) {
            return "Invalid mark. Must be between 0 and 100.";
        }

        marks.set(subjectIndex, newMark);
        student.setMarks(marks); // This also recalculates grade
        studentRepository.save(student);
        return "Marks updated successfully.";
    }


    public String deleteStudent(Long rollNumber) {
        if (!studentRepository.existsById(rollNumber)) {
            return "Student not found.";
        }
        studentRepository.deleteById(rollNumber);
        return "Student deleted successfully.";
    }

    public void updateAllGrades() {
        List<Student> allStudents = studentRepository.findAll();
        for (Student student : allStudents) {
            student.setMarks(student.getMarks()); // Recalculates grade
        }
        studentRepository.saveAll(allStudents);
    }
}
