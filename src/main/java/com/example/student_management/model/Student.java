package com.example.student_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "STUDENT")
@Getter
@Setter
public class Student {

    @Id
    private Long rollNumber;

    private String name;

    @ElementCollection
    private List<Integer> marks;

    private char grade;

    public Student() {
    }

    public Student(Long rollNumber, String name, List<Integer> marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }


    public void setMarks(List<Integer> marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }


    private char calculateGrade(List<Integer> marks) {
        int total = marks.stream().mapToInt(Integer::intValue).sum();
        int average = total / marks.size();

        if (average >= 80) {
            return 'A';
        } else if (average >= 60) {
            return 'B';
        } else if (average >= 40) {
            return 'C';
        } else {
            return 'F';
        }
    }
}
