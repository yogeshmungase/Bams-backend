package com.management.studentattendancesystem.base.rest.model.request;

public class InstitutionDTO {

    private String institutionId;

    private String institutionName;

    private Integer allowedUser;

    private boolean userCreationAllowed;

    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
