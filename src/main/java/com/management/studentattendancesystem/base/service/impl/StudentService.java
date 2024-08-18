package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.rest.mapper.Document;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTOPagination;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<StudentDTO> registerStudent(StudentDTO student);

    ResponseEntity<StudentDTO> getStudentDetailsAgainstId(Long studentId);

    ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(StudentDTO studentDto);

    ResponseEntity<StudentDTOPagination> getStudentListAgainstBatch(Long batchId, Integer offset, Integer limit);

    ResponseEntity<Document> getStudentThumbPdfAgainstBatch(Long batchId, String imageType);

    ResponseEntity<StudentDTO> enhanceBitmapImage(StudentDTO student);

}
