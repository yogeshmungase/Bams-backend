package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.mapper.Document;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTOPagination;
import com.management.studentattendancesystem.base.service.impl.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/sas/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody @Valid StudentDTO student) {
        logger.info("ENTER StudentController:createStudent() with details {}", student);
        return studentService.registerStudent(student);
    }


    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentDetails(@PathVariable("studentId") Long studentId) {
        logger.info("ENTER StudentController:getStudentDetails() with details {}", studentId);
        return studentService.getStudentDetailsAgainstId(studentId);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<StudentDTOPagination> getStudentListAgainstBatch(@PathVariable("batchId") Long batchId,
                                                                           @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        logger.info("ENTER StudentController:getStudentListAgainstBatch() with details batchId : {} , offset : {} , limit :{}", batchId, offset, limit);
        return studentService.getStudentListAgainstBatch(batchId, offset, limit);
    }


    @PostMapping("/verify")
    public ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(@RequestBody StudentDTO studentDto) {
        logger.info("ENTER StudentController:verifyStudentUsingFingerPrint() with details {}", studentDto);
        return studentService.verifyStudentUsingFingerPrint(studentDto);
    }

    @GetMapping("/pdf/{batchId}/{imageType}/{password}")
    public ResponseEntity<Document> getStudentThumbPdf(@PathVariable("batchId") Long batchId, @PathVariable("imageType") String imageType,@PathVariable("password") String password) {
        logger.info("ENTER StudentController:getStudentThumbPdf() with details batchId :{} and {}", batchId, imageType);
        return studentService.getStudentThumbPdfAgainstBatch(batchId, imageType,password);
    }

    @PostMapping("/enhanceImage")
    public ResponseEntity<StudentDTO> enhanceBitmapImage(@RequestBody StudentDTO student) {
        logger.info("ENTER StudentController:enhanceBitmapImage() with details {}", student);
        return studentService.enhanceBitmapImage(student);
    }


}
