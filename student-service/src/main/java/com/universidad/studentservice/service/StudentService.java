package com.universidad.studentservice.service;

import com.universidad.studentservice.model.Student;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {
    Mono<Student> createStudent(Student student);
    Mono<Student> updateStudent(String studentId, Student student);
    Mono<Student> findByStudentId(String studentId);
    Mono<Student> findByUserId(String userId);
    Flux<Student> findAll(PageRequest pageRequest); // Paginación
    Mono<Void> deleteStudent(String studentId);
}
