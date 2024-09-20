package com.management.studentattendancesystem.base.db.model;

import jakarta.persistence.*;


@Entity
@Table(name = "centers")
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "center_name")
    private String centerName;

    @Column(name = "status")
    private String status;

    @Column(name = "institutionId")
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
        return "Center{" +
                "id=" + id +
                ", centerName='" + centerName + '\'' +
                ", status='" + status + '\'' +
                ", institutionId='" + institutionId + '\'' +
                '}';
    }
}
