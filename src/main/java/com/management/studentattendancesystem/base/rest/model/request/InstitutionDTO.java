package com.management.studentattendancesystem.base.rest.model.request;

import com.management.studentattendancesystem.base.db.model.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.List;

public class InstitutionDTO {

    @NotBlank
    private String institutionId;

    @NotBlank
    private String institutionName;

    private Integer allowedUser;

    private boolean userCreationAllowed;

    @NotBlank
    private String status;

    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
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

    public boolean isUserCreationAllowed() {
        return userCreationAllowed;
    }

    public void setUserCreationAllowed(boolean userCreationAllowed) {
        this.userCreationAllowed = userCreationAllowed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InstitutionDTO{" +
                "institutionId='" + institutionId + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", allowedUser=" + allowedUser +
                ", userCreationAllowed=" + userCreationAllowed +
                ", status='" + status + '\'' +
                '}';
    }
}
