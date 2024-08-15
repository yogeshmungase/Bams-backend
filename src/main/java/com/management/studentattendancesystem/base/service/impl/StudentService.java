package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.rest.mapper.Document;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<StudentDTO> registerStudent(StudentDTO student);

    ResponseEntity<StudentDTO> getStudentDetailsAgainstId(Long studentId);

    ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(StudentDTO studentDto);

    ResponseEntity<List<StudentDTO>> getStudentListAgainstBatch(Long batchId);

    ResponseEntity<Document> getStudentThumbPdf(Long batchId);

    ResponseEntity<StudentDTO> enhanceBitmapImage(StudentDTO student);
}
