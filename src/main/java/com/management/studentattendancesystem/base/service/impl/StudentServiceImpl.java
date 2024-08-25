package com.management.studentattendancesystem.base.service.impl;


import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.factory.HtmlToPdfConverter;
import com.management.studentattendancesystem.base.factory.TemplateFactory;
import com.management.studentattendancesystem.base.imgenhancer.GaborFilter;
import com.management.studentattendancesystem.base.repository.StudentRepository;
import com.management.studentattendancesystem.base.rest.mapper.Document;
import com.management.studentattendancesystem.base.rest.mapper.StudentThumbDetails;
import com.management.studentattendancesystem.base.rest.mapper.ThumbPDFMapper;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTOPagination;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<StudentDTO> registerStudent(StudentDTO studentDto) {
        try {
            logger.info("inside the register student with details : {}", studentDto);
            Student student = new Student();
            student.setFirstName(studentDto.getFirstName());
            student.setMiddleName(studentDto.getMiddleName());
            student.setLastName(studentDto.getLastName());
            student.setAddress(studentDto.getAddress());
            student.setMobile(studentDto.getMobile());
            student.setEmail(studentDto.getEmail());
            student.setBatchId(studentDto.getBatchId());
            student.setStudentAttendanceId(studentDto.getStudentAttendanceId());
            student.setActive(Boolean.TRUE.booleanValue());

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
            studentDto.setStudentId(student.getId());
            studentDto.setActive(student.isActive());
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occured while registering student with probable cause : {}", e.getMessage());
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
                studentDTO.setStudentAttendanceId(student.getStudentAttendanceId());
                studentDTO.setFirstName(student.getFirstName());
                studentDTO.setMiddleName(student.getMiddleName());
                studentDTO.setLastName(student.getLastName());
                studentDTO.setEmail(student.getEmail());
                studentDTO.setMobile(student.getMobile());
                studentDTO.setAddress(student.getAddress());
                studentDTO.setActive(student.isActive());
                studentDTO.setStudentId(student.getId());
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

    @Override
    public ResponseEntity<StudentDTOPagination> getStudentListAgainstBatch(Long batchId, Integer offset, Integer limit) {
        logger.info("inside getStudentListAgainstBatch() with batchId : {}", batchId);

        // if limit is zero then set limit and offset
        if (limit == 0) {
            limit = 30;
            if (offset > 0) {
                offset = 0;
            }
        }
        List<Tuple> allByBatchIdAndIsActive = studentRepository.findAllByBatchIdAndIsActive(batchId, Boolean.TRUE.booleanValue());
        if (!CollectionUtils.isEmpty(allByBatchIdAndIsActive)) {
            logger.info("Student count against BatchId : {} is : {}", batchId, allByBatchIdAndIsActive.size());
            StudentDTOPagination studentDTOPagination = new StudentDTOPagination();
            studentDTOPagination.setStudentDTOS(UserMapper.getStudentDTOFromTuple(allByBatchIdAndIsActive));
            studentDTOPagination.setTotalRecords(allByBatchIdAndIsActive.size());
            // setPaginationParameter(studentDTOPagination, offset, limit, batchId);
            return new ResponseEntity<>(studentDTOPagination, HttpStatus.OK);
        } else {
            logger.error("No student details found against batchId : {}", batchId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private void setPaginationParameter(StudentDTOPagination studentDTOPagination, Integer offset, Integer limit, Long batchId) {

        Integer totalStudentCount = studentRepository.getTotalStudentCountAgainstBatch(batchId);
        studentDTOPagination.setTotalRecords(totalStudentCount);
        studentDTOPagination.setPageSize(10);

        //means calling  first time
        if (offset == 0) {
            if (totalStudentCount > studentDTOPagination.getStudentDTOS().size()) {
                Integer nextOffset = offset + studentDTOPagination.getStudentDTOS().size();
                studentDTOPagination.setNextOffset(nextOffset);
                studentDTOPagination.setNextLimit(studentDTOPagination.getPageSize());
                studentDTOPagination.setNextPagesAvailable(true);
            } else {
                studentDTOPagination.setNextOffset(0);
                studentDTOPagination.setNextLimit(0);
                studentDTOPagination.setNextPagesAvailable(false);
            }

        } else if (totalStudentCount > offset) {
            Integer nextOffset = offset + studentDTOPagination.getStudentDTOS().size();

            if (totalStudentCount > nextOffset) {
                studentDTOPagination.setNextPagesAvailable(true);
                studentDTOPagination.setNextOffset(nextOffset);
                studentDTOPagination.setNextLimit(studentDTOPagination.getPageSize());
            } else {
                studentDTOPagination.setNextOffset(nextOffset);
                studentDTOPagination.setNextLimit(0);
                studentDTOPagination.setNextPagesAvailable(false);
            }
        }

        logger.info("Parameter for Pagination : Total student count : {} , offset : {} ,limit : {} against batchId {}", totalStudentCount,
                studentDTOPagination.getNextOffset(), studentDTOPagination.getNextLimit(), batchId);
    }

    @Override
    public ResponseEntity<Document> getStudentThumbPdfAgainstBatch(Long batchId, String imageType) {

        List<Student> studentList = studentRepository.findAllByBatchId(batchId);
        if (!CollectionUtils.isEmpty(studentList)) {
            logger.info("Student record count is :{} against Batch Id is : {} ", studentList.size(), batchId);
            StudentThumbDetails studentThumbDetails = UserMapper.getStudentThumbDetails(studentList, imageType);
            String studentThumbHtmlDocument = getStudentThumbHtmlDocument(studentThumbDetails, "templates/Thumb.vm");

            byte[] bytes = HtmlToPdfConverter.generatePdfByteArray(studentThumbHtmlDocument, "templates/Thumb.vm");
            String base64String = null;
            if (null != bytes) {
                //encode pdf bytes to base64
                base64String = new String(Base64.getEncoder().encode(bytes));
            }
            Document document = new Document();
            document.setDocId("A101");
            document.setDocName("StudentThumbPDF");
            document.setStatus("FAILED");
            if (null != base64String) {
                document.setStatus("SUCCESS");
                document.setBase64Code(base64String);

                try {
                    File file = new File("sthumb.pdf");
                    FileOutputStream fop = new FileOutputStream(file);

                    fop.write(bytes);
                    fop.flush();
                    fop.close();
                } catch (Exception e) {

                }
            }
            return new ResponseEntity<>(document, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private String getStudentThumbHtmlDocument(StudentThumbDetails studentThumbDetails, String templatePath) {

        Template template = new Template();
        StringWriter writer = null;

        try {
            TemplateFactory templatesFactory = new TemplateFactory();

            template = templatesFactory.fetchTemplate(templatePath);

            VelocityContext velocityContext = ThumbPDFMapper.mapStudentThumbDetails(studentThumbDetails);

            writer = new StringWriter();
            template.merge(velocityContext, writer);

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            writer.flush();
            try {
                if (null != writer)
                    writer.close();
            } catch (IOException e) {
            }

        }
    }

    @Override
    public ResponseEntity<StudentDTO> enhanceBitmapImage(StudentDTO student) {
        StudentDTO output = new StudentDTO();
        if (null != student.getThumb1()) {
            byte[] normalImageByteArray = Base64.getDecoder().decode(student.getThumb1());
            byte[] enhancedImageByteArray = GaborFilter.enhanceImage(normalImageByteArray);
            output.setThumb1(Base64.getEncoder().encodeToString(enhancedImageByteArray));
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
