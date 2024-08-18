package com.management.studentattendancesystem.base.rest.model.request;

import java.util.List;

public class StudentDTOPagination {

    private List<StudentDTO> studentDTOS;
    private Integer totalRecords;
    private Integer pageSize;

    private Integer nextOffset;

    private Integer nextLimit;

    private boolean nextPagesAvailable;

    public boolean isNextPagesAvailable() {
        return nextPagesAvailable;
    }

    public void setNextPagesAvailable(boolean nextPagesAvailable) {
        this.nextPagesAvailable = nextPagesAvailable;
    }

    public List<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }

    public void setStudentDTOS(List<StudentDTO> studentDTOS) {
        this.studentDTOS = studentDTOS;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public Integer getNextLimit() {
        return nextLimit;
    }

    public void setNextLimit(Integer nextLimit) {
        this.nextLimit = nextLimit;
    }
}
