package com.management.studentattendancesystem.base.service.impl;


import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.repository.StudentRepository;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<StudentDTO> registerStudent(StudentDTO studentDto) {
        try {
            Student student = new Student();
            student.setFirstName(studentDto.getFirstName());
            student.setMiddleName(studentDto.getMiddleName());
            student.setLastName(studentDto.getLastName());
            student.setAddress(studentDto.getAddress());
            student.setMobile(studentDto.getMobile());
            student.setEmail(studentDto.getEmail());
            student.setBatchId(studentDto.getBatchId());

            if (null != studentDto.getThumb1()) {
                student.setThumb1(Base64.getDecoder().decode(studentDto.getThumb1()));
            }
            if (null != studentDto.getThumb2()) {
                student.setThumb2(Base64.getDecoder().decode(studentDto.getThumb2()));
            }
            if (null != studentDto.getThumb3()) {
                student.setThumb3(Base64.getDecoder().decode(studentDto.getThumb3()));
            }
            if (null != studentDto.getThumb4()) {
                student.setThumb4(Base64.getDecoder().decode(studentDto.getThumb4()));
            }
            if (null != studentDto.getThumb5()) {
                student.setThumb5(Base64.getDecoder().decode(studentDto.getThumb5()));
            }

            studentRepository.save(student);
            logger.info("Saving student with details {}", student);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<StudentDTO> getStudentDetailsAgainstId(Long studentId) {
        Optional<Student> byId = studentRepository.findById(studentId);
        try {
            if (byId.isPresent()) {
                Student student = byId.get();
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setBatchId(student.getBatchId());
                studentDTO.setFirstName(student.getFirstName());
                studentDTO.setMiddleName(student.getMiddleName());
                studentDTO.setLastName(student.getLastName());
                studentDTO.setEmail(student.getEmail());
                studentDTO.setMobile(student.getMobile());
                studentDTO.setAddress(student.getAddress());
                if (null != student.getThumb1()) {
                    studentDTO.setThumb1(Base64.getEncoder().encodeToString(student.getThumb1()));
                }
                if (null != student.getThumb2()) {
                    studentDTO.setThumb2(Base64.getEncoder().encodeToString(student.getThumb2()));
                }
                if (null != student.getThumb3()) {
                    studentDTO.setThumb3(Base64.getEncoder().encodeToString(student.getThumb3()));
                }
                if (null != student.getThumb4()) {
                    studentDTO.setThumb4(Base64.getEncoder().encodeToString(student.getThumb4()));
                }
                if (null != student.getThumb5()) {
                    studentDTO.setThumb5(Base64.getEncoder().encodeToString(student.getThumb5()));
                }
                logger.info("Returning student info : {}", studentDTO);
                return new ResponseEntity<>(studentDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponse> verifyStudentUsingFingerPrint(StudentDTO studentDto) {
        GenericResponse genericResponse = new GenericResponse();
        List<Student> studentList = studentRepository.findAll();

        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(Base64.getDecoder().decode(studentDto.getThumb1())));

        for (Student student : studentList) {
            if (null != student.getThumb1()) {
                FingerprintTemplate candidate = new FingerprintTemplate(
                        new FingerprintImage(student.getThumb1()));

                FingerprintMatcher fingerprintMatcher = new FingerprintMatcher(probe);

                double match = fingerprintMatcher.match(candidate);
                // Output the result
                logger.info("Similarity Score: {} and Student Id is {}", match, student.getId());

                // Threshold for match (example value)
                double threshold = 40;
                if (match >= threshold) {
                    logger.info("Matched student id {}", student.getId());
                    genericResponse.setStatus("SUCCESS");
                    genericResponse.setMessage("Fingerprints match!");
                } else {
                    genericResponse.setStatus("FAILED");
                    genericResponse.setMessage("Fingerprints do not match.");
                }
            } else {
                genericResponse.setStatus("FAILED");
                genericResponse.setMessage("Record not found");
            }
        }
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
