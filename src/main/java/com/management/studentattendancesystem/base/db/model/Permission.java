package com.management.studentattendancesystem.base.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "permissions")
public class Permission extends ControlFields implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;
    @Column(name = "view_access")
    private String viewAccess;

    @Column(name = "add_access")
    private String addAccess;

    @Column(name = "update_access")
    private String updateAccess;

    @Column(name = "delete_access")
    private String deleteAccess;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles ;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Permission() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getViewAccess() {
        return viewAccess;
    }

    public void setViewAccess(String viewAccess) {
        this.viewAccess = viewAccess;
    }

    public String getAddAccess() {
        return addAccess;
    }

    public void setAddAccess(String addAccess) {
        this.addAccess = addAccess;
    }

    public String getUpdateAccess() {
        return updateAccess;
    }

    public void setUpdateAccess(String updateAccess) {
        this.updateAccess = updateAccess;
    }

    public String getDeleteAccess() {
        return deleteAccess;
    }

    public void setDeleteAccess(String deleteAccess) {
        this.deleteAccess = deleteAccess;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", viewAccess='" + viewAccess + '\'' +
                ", addAccess='" + addAccess + '\'' +
                ", updateAccess='" + updateAccess + '\'' +
                ", deleteAccess='" + deleteAccess + '\'' +
                ", roles=" + roles +
                '}';
    }
}
