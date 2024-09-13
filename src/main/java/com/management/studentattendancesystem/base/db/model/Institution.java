package com.management.studentattendancesystem.base.db.model;

import jakarta.persistence.*;

import java.util.Collection;


@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @Column(name = "institution_id")
    private String institutionId;

    @Column(name = "institution_name")
    private String institutionName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "inst_roles",
            joinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "institution_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> inst_roles;

    @Column(name = "allowed_user")
    private Integer allowedUser;

    @Column(name = "user_creation_allowed")
    private boolean userCreationAllowed;

    @Column(name = "status")
    private String status;


    public Institution() {
    }


    public Collection<Role> getInst_roles() {
        return inst_roles;
    }

    public void setInst_roles(Collection<Role> inst_roles) {
        this.inst_roles = inst_roles;
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
