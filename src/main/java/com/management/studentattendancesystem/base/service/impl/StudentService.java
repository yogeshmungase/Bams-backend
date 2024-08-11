package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<StudentDTO> registerStudent(StudentDTO student);

    ResponseEntity<StudentDTO> getStudentDetailsAgainstId(Long studentId);

    ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(StudentDTO studentDto);
}
