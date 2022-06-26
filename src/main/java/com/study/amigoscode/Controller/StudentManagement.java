package com.study.amigoscode.Controller;

import com.study.amigoscode.Domain.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagement {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student (1, "Cristal Silva"),
            new Student(2, "Gox Felício"),
            new Student(3, "Véia Gusmão")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println();
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student){
        System.out.printf("%s %s%n", studentId, student);
    }
}

