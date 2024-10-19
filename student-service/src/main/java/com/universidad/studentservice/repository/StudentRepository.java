package com.universidad.studentservice.repository;

import com.universidad.studentservice.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
    Mono<Student> findByUserId(String userId);
    Flux<Student> findAllBy();
}
