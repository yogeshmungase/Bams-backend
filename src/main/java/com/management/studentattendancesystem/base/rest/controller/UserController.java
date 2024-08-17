package com.management.studentattendancesystem.base.rest.controller;

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
public class UserController implements UsersApi {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersService usersService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UsersApi.super.getRequest();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return usersService.createUser(user);
    }


    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userid) {
        return usersService.deleteUser(userid);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<User>> getUsers() {

        return usersService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        return usersService.getUserById(userId);
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(String userId, User user) {
        return usersService.updateUser(userId, user);
    }
}
