package com.management.studenattendancesystem.base.service;

import com.management.studenattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studenattendancesystem.base.rest.model.Response.LoginResponse;
import com.management.studenattendancesystem.base.rest.model.request.LoginRequest;
import com.dox.ail.base.rest.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    /**
     *
     * @param loginRequest
     * @return
     */
    ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest);

    /**
     *
     * @param registrationRequest
     * @return
     */
    ResponseEntity<GenericResponse> registerUser(User registrationRequest);
}
