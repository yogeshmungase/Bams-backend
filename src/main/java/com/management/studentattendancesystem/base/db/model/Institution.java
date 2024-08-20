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

    @Column(name = "allowedUser")
    private Integer allowedUser;

    @Column(name = "user_creation_allowed")
    private boolean userCreationAllowed;

    @Column(name = "is_active")
    private boolean enabled;


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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
                ", enabled=" + enabled +
                '}';
    }
}
