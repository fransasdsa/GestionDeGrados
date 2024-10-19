package com.universidad.studentservice.controller;

import com.universidad.studentservice.model.Student;
import com.universidad.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ESTUDIANTE')")
    public Mono<Student> createStudent(@Valid @RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Student> getStudentById(@PathVariable String studentId) {
        return studentService.findByStudentId(studentId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Student> getStudentByUserId(@PathVariable String userId) {
        return studentService.findByUserId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Flux<Student> getAllStudents(@RequestParam("page") int page, @RequestParam("size") int size) {
        return studentService.findAll(PageRequest.of(page, size));
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public Mono<Student> updateStudent(@PathVariable String studentId, @Valid @RequestBody Student student) {
        return studentService.updateStudent(studentId, student);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Void> deleteStudent(@PathVariable String studentId) {
        return studentService.deleteStudent(studentId);
    }
}
