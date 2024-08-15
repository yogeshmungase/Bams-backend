package com.management.studentattendancesystem.base.rest.mapper;

import java.util.List;

public class StudentThumbDetails {

   private List<StudentThumbProperties> thumbPropertiesList;

    public List<StudentThumbProperties> getThumbPropertiesList() {
        return thumbPropertiesList;
    }

    public void setThumbPropertiesList(List<StudentThumbProperties> thumbPropertiesList) {
        this.thumbPropertiesList = thumbPropertiesList;
    }

    @Override
    public String toString() {
        return "StudentThumbDetails{" +
                "thumbPropertiesList=" + thumbPropertiesList +
                '}';
    }
}
