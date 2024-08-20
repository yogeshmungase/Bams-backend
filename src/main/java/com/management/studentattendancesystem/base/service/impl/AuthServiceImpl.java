package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.db.model.User;
import com.management.studentattendancesystem.base.repository.UserRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.Response.LoginResponse;
import com.management.studentattendancesystem.base.rest.model.UserDetailsCred;
import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import com.management.studentattendancesystem.base.rest.model.request.LoginRequest;
import com.management.studentattendancesystem.base.jwt.JwtService;
import com.management.studentattendancesystem.base.service.AuthService;
import com.management.studentattendancesystem.base.service.InstitutionService;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private InstitutionService institutionService;



    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        Authentication authenticate = null;
        LoginResponse loginResponse = new LoginResponse();
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            logger.error("Exception occur while authentication : {}",exception.getMessage());
            loginResponse.setStatus(Constants.FAILED);
            loginResponse.setMessage(Constants.LOGIN_FAILED);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }

        Optional<User> authenticatedUser = userRepository.findByEmailAndStatusActive(loginRequest.getEmail());

        if (authenticate.isAuthenticated() && authenticatedUser.isPresent()) {
            String jwtToken = jwtService.generateToken(new UserDetailsCred(authenticatedUser.get()));
            loginResponse.setToken(jwtToken);
            loginResponse.setUser(authenticatedUser.get());
            loginResponse.setStatus(Constants.SUCCESS);
            loginResponse.setMessage(Constants.LOGIN_SUCCESSFULLY);
        } else {
            loginResponse.setStatus(Constants.FAILED);
            loginResponse.setMessage(Constants.LOGIN_FAILED);
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResponse> registerUser(com.dox.ail.base.rest.model.User registrationRequest) {

        GenericResponse genericResponse = new GenericResponse();
        if (null != registrationRequest.getInstitutionId()) {
            genericResponse = institutionService.validateInstitution(registrationRequest.getInstitutionId());
            if ("FAILED".equalsIgnoreCase(genericResponse.getStatus())) {
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            }
        }

        Optional<User> userById = userRepository.findByEmailAndStatusActive(registrationRequest.getEmail());

        if (userById.isPresent()) {
            genericResponse.setStatus(Constants.FAILED);
            genericResponse.setMessage("Email already exist !!!");
        }else {
            User user = UserMapper.convertToDBUser(registrationRequest);
            logger.info("Mapped User : {}", user);
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setStatus(Constants.ACTIVE);
            userRepository.save(user);
            logger.info("Mapped User After save: {}", user);
            genericResponse.setStatus(Constants.SUCCESS);
            genericResponse.setMessage("User Registered successfully");
        }


        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
