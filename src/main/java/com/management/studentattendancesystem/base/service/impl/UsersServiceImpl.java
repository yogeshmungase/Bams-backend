package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.repository.UserRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.service.InstitutionService;
import com.management.studentattendancesystem.base.service.UsersService;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import com.dox.ail.base.rest.model.Role;
import com.dox.ail.base.rest.model.User;
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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InstitutionService institutionService;


    @Override
    public ResponseEntity<GenericResponse> createUser(User userRequest) {

        GenericResponse genericResponse = new GenericResponse();
        if (null != userRequest.getInstitutionId()) {
            genericResponse = institutionService.validateInstitution(userRequest.getInstitutionId());
            if ("FAILED".equalsIgnoreCase(genericResponse.getStatus())) {
                logger.error("New registration failed due to error : {}", genericResponse);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            }
        }
        Optional<com.management.studentattendancesystem.base.db.model.User> userByEmail = userRepository.findByEmailAndStatusActive(userRequest.getEmail());
        Optional<com.management.studentattendancesystem.base.db.model.User> userByMobile = userRepository.findByUserNameAndStatusActive(userRequest.getUsername());
        if (userByEmail.isPresent() || userByMobile.isPresent()) {
            logger.error("User already present against");
            genericResponse.setMessage("Email or userName already exist !!!!");
            genericResponse.setStatus("FAILED");
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        }
        com.management.studentattendancesystem.base.db.model.User user = UserMapper.convertToDBUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequest.setPassword(user.getPassword());
        user.setStatus(Constants.ACTIVE);
        userRepository.save(user);
        logger.info("Mapped User After save: {}", user);

        genericResponse.setStatus("SUCCESS");
        genericResponse.setMessage("User created successfully");

        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        com.management.studentattendancesystem.base.db.model.User dbUser = userRepository.findByIdAndStatus(Long.valueOf(userId), Constants.ACTIVE);
        if (dbUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        dbUser.setStatus(Constants.DELETE);
        userRepository.save(dbUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers(String institutionId) {
        List<com.management.studentattendancesystem.base.db.model.User> allUser = userRepository.findAllByInstitutionId(institutionId);
        if (!CollectionUtils.isEmpty(allUser)) {
            return new ResponseEntity<>(UserMapper.convertToModelUsers(allUser), HttpStatus.OK);
        }
        logger.warn("No Users found against institutionId :{}", institutionId);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<User> updateUser(String userId, User userRequest) {
        Optional<com.management.studentattendancesystem.base.db.model.User> byId = userRepository.findById(Long.valueOf(userRequest.getId()));

        if (!byId.isPresent()) {
            return new ResponseEntity<>(userRequest, HttpStatus.BAD_REQUEST);
        }

        com.management.studentattendancesystem.base.db.model.User dbUser = byId.get();
        List<com.management.studentattendancesystem.base.db.model.Role> dbRoleList = new ArrayList<>();
        for (Role role : userRequest.getRoles()) {
            com.management.studentattendancesystem.base.db.model.Role dbRole = new com.management.studentattendancesystem.base.db.model.Role(role.getName());
            dbRole.setId(Long.valueOf(role.getId()));
            dbRoleList.add(dbRole);
        }
        dbUser.setRoles(dbRoleList);
        dbUser.setStatus(userRequest.getStatus());
        userRepository.save(dbUser);
        logger.info("User updated successfully with details userId : {}", userId);
        return new ResponseEntity<>(userRequest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUserById(String userId) {
        com.management.studentattendancesystem.base.db.model.User dbUser = userRepository.findByIdAndStatus(Long.valueOf(userId), Constants.ACTIVE);
        if (null == dbUser) {
            return new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(UserMapper.convertToModelUser(dbUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResponse> operatePassword(User user, String operationType) {
        Authentication authenticate = null;
        GenericResponse genericResponse = new GenericResponse();

        Optional<com.management.studentattendancesystem.base.db.model.User> authenticatedUser = userRepository.findByEmailAndStatusActive(user.getEmail());

        String newPassword = user.getNewPassword();

        if (null == newPassword || newPassword.isEmpty()
                || !newPassword.equalsIgnoreCase(user.getConfirmNewPassword())) {
            genericResponse.setStatus(Constants.FAILED);
            genericResponse.setMessage(Constants.INVALID_PASSWORD);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        }
        if (authenticatedUser.isPresent()) {
            if ("CHANGE".equalsIgnoreCase(operationType)) {

                try {
                    authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
                } catch (AuthenticationException exception) {
                    logger.error("Exception occur while authentication : {}", exception.getMessage());
                    genericResponse.setStatus(Constants.FAILED);
                    genericResponse.setMessage(Constants.CURRENT_PASSWORD_INCORRECT);
                    return new ResponseEntity<>(genericResponse, HttpStatus.OK);
                }

                if (authenticate.isAuthenticated()) {
                    com.management.studentattendancesystem.base.db.model.User dbUser = authenticatedUser.get();
                    dbUser.setPassword(passwordEncoder.encode(user.getConfirmNewPassword()));
                    dbUser.setUpdatedAt(LocalDateTime.now());
                    dbUser.setUpdatedBy(user.getUpdatedBy());
                    userRepository.save(dbUser);
                }

                genericResponse.setStatus(Constants.SUCCESS);
                genericResponse.setMessage(Constants.PASSWORD_UPDATION_SUCCESSFULL);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);

            } else if ("RESET".equalsIgnoreCase(operationType)) {

                com.management.studentattendancesystem.base.db.model.User dbUser = authenticatedUser.get();
                dbUser.setPassword(passwordEncoder.encode(user.getConfirmNewPassword()));
                dbUser.setUpdatedAt(LocalDateTime.now());
                dbUser.setUpdatedBy(user.getUpdatedBy());
                userRepository.save(dbUser);


                genericResponse.setStatus(Constants.SUCCESS);
                genericResponse.setMessage(Constants.PASSWORD_UPDATION_SUCCESSFULL);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }
}
