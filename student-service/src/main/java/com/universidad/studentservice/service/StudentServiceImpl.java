package com.universidad.studentservice.service;

import com.universidad.studentservice.model.Student;
import com.universidad.studentservice.repository.StudentRepository;
import com.universidad.studentservice.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Mono<Student> createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Mono<Student> updateStudent(String studentId, Student student) {
        return studentRepository.findById(studentId)
                .flatMap(existingStudent -> {
                    existingStudent.setPrograma(student.getPrograma());
                    existingStudent.setLineaInvestigacionId(student.getLineaInvestigacionId());
                    existingStudent.setEtapaInscripcion(student.getEtapaInscripcion());
                    existingStudent.setPagos(student.getPagos());
                    existingStudent.setProyectoId(student.getProyectoId());
                    existingStudent.setGrupoId(student.getGrupoId());
                    return studentRepository.save(existingStudent);
                });
    }

    @Override
    public Mono<Student> findByStudentId(String studentId) {
        return studentRepository.findById(studentId)
                .switchIfEmpty(Mono.error(new StudentNotFoundException("Estudiante con ID " + studentId + " no encontrado")));
    }

    @Override
    public Mono<Student> findByUserId(String userId) {
        return studentRepository.findByUserId(userId);
    }

    @Override
    public Flux<Student> findAll(PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return studentRepository.findAllBy()
                .skip((long) page * size)
                .take(size);
    }

    @Override
    public Mono<Void> deleteStudent(String studentId) {
        return studentRepository.deleteById(studentId);
    }
}
