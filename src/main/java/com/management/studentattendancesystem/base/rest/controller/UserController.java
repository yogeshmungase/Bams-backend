package com.management.studentattendancesystem.base.rest.controller;

import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.service.UsersService;
import com.dox.ail.base.rest.UsersApi;
import com.dox.ail.base.rest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/sas/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersService usersService;


    @PostMapping
    public ResponseEntity<GenericResponse> createUser(@RequestBody User user) {
        logger.info("Inside createUser() with details : {}",user);
        return usersService.createUser(user);
    }

    @PostMapping("credential/{operationType}")
    public ResponseEntity<GenericResponse> operatePassword(@PathVariable("operationType") String operationType, @RequestBody User user) {
        logger.info("Inside createUser() with details : {}", user);
        return usersService.operatePassword(user,operationType);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userid) {
        logger.info("Inside deleteUser() with details : {}",userid);
        return usersService.deleteUser(userid);
    }

    @GetMapping("/inst/{institutionId}")
    public ResponseEntity<List<User>> getUsersAgainstInstitutionId(@PathVariable("institutionId") String institutionId) {
        logger.info("Inside getUsersAgainstInstitutionId() with details : {}",institutionId);
        return usersService.getAllUsers(institutionId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        logger.info("Inside getUserById() with details : {}", userId);
        return usersService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId")String userId, @RequestBody User user) {
        logger.info("Inside updateUser() with details userId: {}, and details : {}", userId,user );
        return usersService.updateUser(userId, user);
    }
}
