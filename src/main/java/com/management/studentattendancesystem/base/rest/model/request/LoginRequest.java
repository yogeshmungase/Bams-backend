package com.management.studentattendancesystem.base.rest.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Valid
    @NotBlank(message = "email field is mandatory")
    private String email;

    @Valid
    @NotBlank(message = "password field is mandatory")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest() {
    }
}
