package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        String response = studentService.addStudent(student);
        if ("Student added successfully.".equals(response)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{rollNumber}")
    public ResponseEntity<?> getStudent(@PathVariable Long rollNumber) {
        Student student = studentService.getStudentByRollNumber(rollNumber);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(404).body("Student not found.");
    }

    @PutMapping("/{rollNumber}/marks")
    public ResponseEntity<String> updateMarks(@PathVariable Long rollNumber,
                                              @RequestParam int subjectIndex,
                                              @RequestParam int newMark) {
        if (subjectIndex < 0 || subjectIndex > 2) {
            return ResponseEntity.badRequest().body("Invalid subject index. Must be between 0 and 2.");
        }
        if (newMark < 0 || newMark > 100) {
            return ResponseEntity.badRequest().body("Invalid mark. Must be between 0 and 100.");
        }

        String response = studentService.updateMarks(rollNumber, subjectIndex, newMark);
        if ("Marks updated successfully.".equals(response)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long rollNumber) {
        String response = studentService.deleteStudent(rollNumber);
        if ("Student deleted successfully.".equals(response)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    @PutMapping("/grades")
    public ResponseEntity<String> updateAllGrades() {
        studentService.updateAllGrades();
        return ResponseEntity.ok("Grades updated for all students.");
    }
}
