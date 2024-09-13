package com.management.studentattendancesystem.base.imgenhancer;

import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.rest.mapper.StudentThumbProperties;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.Callable;

public class ImageProcessing {

    private static StudentThumbProperties processImage(Student student, String imageType) throws IOException {
        StudentThumbProperties studentThumbProperties = new StudentThumbProperties();
        if (null != student.getThumb1()) {
            if ("enhanced".equalsIgnoreCase(imageType)) {
                studentThumbProperties.setThumb1(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb1())));
            } else {
                studentThumbProperties.setThumb1(UserMapper.getBitMapBase64String(student.getThumb1()));
            }
        }
        if (null != student.getThumb2()) {
            if ("enhanced".equalsIgnoreCase(imageType)) {
                studentThumbProperties.setThumb2(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb2())));
            } else {
                studentThumbProperties.setThumb2(UserMapper.getBitMapBase64String(student.getThumb2()));
            }
        }
        if (null != student.getThumb3()) {
            if ("enhanced".equalsIgnoreCase(imageType)) {
                studentThumbProperties.setThumb3(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb3())));
            } else {
                studentThumbProperties.setThumb3(UserMapper.getBitMapBase64String(student.getThumb3()));
            }
        }
        if (null != student.getThumb4()) {
            if ("enhanced".equalsIgnoreCase(imageType)) {
                studentThumbProperties.setThumb4(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb4())));
            } else {
                studentThumbProperties.setThumb4(UserMapper.getBitMapBase64String(student.getThumb4()));
            }
        }
        if (null != student.getThumb5()) {
            if ("enhanced".equalsIgnoreCase(imageType)) {
                studentThumbProperties.setThumb5(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb5())));
            } else {
                studentThumbProperties.setThumb5(UserMapper.getBitMapBase64String(student.getThumb5()));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(student.getFirstName())) {
            stringBuilder.append(student.getFirstName()).append(" ");
        }
        if (!StringUtils.isEmpty(student.getLastName())) {
            stringBuilder.append(student.getLastName()).append(" - ");
        }
        if (!StringUtils.isEmpty(student.getStudentAttendanceId())) {
            studentThumbProperties.setAttendanceId(student.getStudentAttendanceId());
        }
        studentThumbProperties.setName(stringBuilder.toString());

        return studentThumbProperties;
    }

    static class ImageProcessingTask implements Callable<StudentThumbProperties> {
        private final Student student;
        private final String imageType;

        public ImageProcessingTask(Student student, String imageType) {
            this.student = student;
            this.imageType = imageType;
        }

        @Override
        public StudentThumbProperties call() throws Exception {
            return processImage(student, imageType);
        }
    }
}
