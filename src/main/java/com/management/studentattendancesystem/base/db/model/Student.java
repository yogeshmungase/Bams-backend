package com.management.studentattendancesystem.base.db.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "student")
public class Student extends ControlFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_attendance_id")
    private String studentAttendanceId;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "thumb1")
    private byte[] thumb1;


    @Column(name = "thumb2")
    @Lob
    private byte[] thumb2;

    @Column(name = "thumb3")
    @Lob
    private byte[] thumb3;


    @Column(name = "thumb4")
    @Lob
    private byte[] thumb4;


    @Column(name = "thumb5")
    @Lob
    private byte[] thumb5;


    @Column(name = "is_active")
    private boolean isActive;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getThumb1() {
        return thumb1;
    }

    public void setThumb1(byte[] thumb1) {
        this.thumb1 = thumb1;
    }

    public byte[] getThumb2() {
        return thumb2;
    }

    public void setThumb2(byte[] thumb2) {
        this.thumb2 = thumb2;
    }

    public byte[] getThumb3() {
        return thumb3;
    }

    public void setThumb3(byte[] thumb3) {
        this.thumb3 = thumb3;
    }

    public byte[] getThumb4() {
        return thumb4;
    }

    public void setThumb4(byte[] thumb4) {
        this.thumb4 = thumb4;
    }

    public byte[] getThumb5() {
        return thumb5;
    }

    public void setThumb5(byte[] thumb5) {
        this.thumb5 = thumb5;
    }


    public String getStudentAttendanceId() {
        return studentAttendanceId;
    }

    public void setStudentAttendanceId(String studentAttendanceId) {
        this.studentAttendanceId = studentAttendanceId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentAttendanceId='" + studentAttendanceId + '\'' +
                ", btachId=" + batchId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", thumb1=" + Arrays.toString(thumb1) +
                ", thumb2=" + Arrays.toString(thumb2) +
                ", thumb3=" + Arrays.toString(thumb3) +
                ", thumb4=" + Arrays.toString(thumb4) +
                ", thumb5=" + Arrays.toString(thumb5) +
                ", isActive=" + isActive +
                '}';
    }
}
