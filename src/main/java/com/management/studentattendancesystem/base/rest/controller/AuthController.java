package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.Response.LoginResponse;
import com.management.studentattendancesystem.base.rest.model.request.LoginRequest;
import com.management.studentattendancesystem.base.service.AuthService;
import com.dox.ail.base.rest.model.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/sas/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Inside loginUser() with details : {}", loginRequest);
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerUser(@RequestBody User registrationRequest) {
        logger.info("Inside registerUser() with details : {}", registrationRequest);
        return authService.registerUser(registrationRequest);
    }
}
