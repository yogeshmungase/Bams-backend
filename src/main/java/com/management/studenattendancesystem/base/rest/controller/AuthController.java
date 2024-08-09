package com.management.studenattendancesystem.base.rest.controller;


import com.management.studenattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studenattendancesystem.base.rest.model.Response.LoginResponse;
import com.management.studenattendancesystem.base.rest.model.request.LoginRequest;
import com.management.studenattendancesystem.base.service.AuthService;
import com.dox.ail.base.rest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/amdil/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerUser(@RequestBody User registrationRequest) {
        return authService.registerUser(registrationRequest);
    }
}
