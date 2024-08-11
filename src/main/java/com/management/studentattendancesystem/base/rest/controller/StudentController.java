package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.service.impl.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/sas/student")
public class StudentController {

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody StudentDTO student) {
        logger.info("ENTER StudentController:createStudent() with details {}", student);
        return studentService.registerStudent(student);
    }


    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentDetails(@PathVariable("studentId") Long studentId) {
        logger.info("ENTER StudentController:getStudentDetails() with details {}", studentId);
        return studentService.getStudentDetailsAgainstId(studentId);
    }


    @PostMapping("/verify")
    public ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(@RequestBody StudentDTO studentDto) {
        logger.info("ENTER StudentController:verifyStudentUsingFingerPrint() with details {}", studentDto);
        return studentService.verifyStudentUsingFingerPrint(studentDto);
    }


}
