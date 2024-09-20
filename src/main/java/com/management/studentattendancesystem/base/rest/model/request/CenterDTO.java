package com.management.studentattendancesystem.base.rest.model.request;


public class CenterDTO {
    private Long id;

    private String centerName;

    private String status;

    private String institutionId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @Override
    public String toString() {
        return "CenterDTO{" +
                "id=" + id +
                ", centerName='" + centerName + '\'' +
                ", status='" + status + '\'' +
                ", institutionId='" + institutionId + '\'' +
                '}';
    }
}
