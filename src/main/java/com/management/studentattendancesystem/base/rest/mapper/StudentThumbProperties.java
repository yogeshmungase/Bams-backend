package com.management.studentattendancesystem.base.rest.mapper;

public class StudentThumbProperties {

    private String thumb1;
    private String thumb2;
    private String thumb3;
    private String thumb4;
    private String thumb5;

    private String name;

    private String attendanceId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    @Override
    public String toString() {
        return "StudentThumbProperties{" +
                ", name='" + name + '\'' +
                ", attendanceId='" + attendanceId + '\'' +
                '}';
    }
}
