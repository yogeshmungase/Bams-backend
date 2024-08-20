package com.management.studentattendancesystem.base.db.model;

import jakarta.persistence.*;


@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @Column(name = "institution_id")
    private String institutionId;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "allowed_user")
    private Integer allowedUser;

    @Column(name = "user_creation_allowed")
    private boolean userCreationAllowed;

    @Column(name = "status")
    private String status;


    public Institution() {
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public Integer getAllowedUser() {
        return allowedUser;
    }

    public void setAllowedUser(Integer allowedUser) {
        this.allowedUser = allowedUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isUserCreationAllowed() {
        return userCreationAllowed;
    }

    public void setUserCreationAllowed(boolean userCreationAllowed) {
        this.userCreationAllowed = userCreationAllowed;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "institutionId='" + institutionId + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", allowedUser=" + allowedUser +
                ", userCreationAllowed=" + userCreationAllowed +
                ", status='" + status + '\'' +
                '}';
    }
}
