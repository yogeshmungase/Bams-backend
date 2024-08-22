package com.management.studentattendancesystem.base.rest.model.request;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {

    private Long studentId;

    @NotBlank(message = "studentAttendanceId is mandatory ")
    private String studentAttendanceId;

    @NotBlank(message = "BatchId is mandatory ")
    private Long batchId;

    @NotBlank(message = "firstName is mandatory ")
    private String firstName;

    @NotBlank(message = "middleName is mandatory ")
    private String middleName;

    @NotBlank(message = "lastName is mandatory ")
    private String lastName;

    private String email;

    private String mobile;

    private String address;

    private boolean active;

    @NotBlank(message = "thumb1 is mandatory ")
    private String thumb1;

    @NotBlank(message = "thumb2 is mandatory ")
    private String thumb2;

    @NotBlank(message = "thumb3 is mandatory ")
    private String thumb3;

    @NotBlank(message = "thumb4 is mandatory ")
    private String thumb4;

    @NotBlank(message = "thumb5 is mandatory ")
    private String thumb5;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThumb1() {
        return thumb1;
    }

    public void setThumb1(String thumb1) {
        this.thumb1 = thumb1;
    }

    public String getThumb2() {
        return thumb2;
    }

    public void setThumb2(String thumb2) {
        this.thumb2 = thumb2;
    }

    public String getThumb3() {
        return thumb3;
    }

    public void setThumb3(String thumb3) {
        this.thumb3 = thumb3;
    }

    public String getThumb4() {
        return thumb4;
    }

    public void setThumb4(String thumb4) {
        this.thumb4 = thumb4;
    }

    public String getThumb5() {
        return thumb5;
    }

    public void setThumb5(String thumb5) {
        this.thumb5 = thumb5;
    }

    public String getStudentAttendanceId() {
        return studentAttendanceId;
    }

    public void setStudentAttendanceId(String studentAttendanceId) {
        this.studentAttendanceId = studentAttendanceId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentId=" + studentId +
                ", studentAttendanceId='" + studentAttendanceId + '\'' +
                ", batchId=" + batchId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                '}';
    }
}
