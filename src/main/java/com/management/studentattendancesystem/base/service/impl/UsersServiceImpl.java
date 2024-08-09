package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.repository.UserRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.service.UsersService;
import com.management.studentattendancesystem.base.utils.constants.AMDILConstant;
import com.dox.ail.base.rest.model.Role;
import com.dox.ail.base.rest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<User> createUser(User userRequest) {
        Optional<com.management.studentattendancesystem.base.db.model.User> userById = userRepository.findByEmailAndStatusActive(userRequest.getEmail());
        if (userById.isPresent()) {
            return new ResponseEntity<>(userRequest, HttpStatus.BAD_REQUEST);
        }

        com.management.studentattendancesystem.base.db.model.User user = UserMapper.convertToDBUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setStatus(AMDILConstant.ACTIVE);
        userRepository.save(user);
        logger.info("Mapped User After save: {}", user);
        return new ResponseEntity<>(userRequest, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        com.management.studentattendancesystem.base.db.model.User dbUser = userRepository.findByIdAndStatus(Long.valueOf(userId), AMDILConstant.ACTIVE);
        if (dbUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        dbUser.setStatus(AMDILConstant.DELETE);
        userRepository.save(dbUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<com.management.studentattendancesystem.base.db.model.User> allUser = userRepository.findAllByStatus(AMDILConstant.ACTIVE);
        if (!CollectionUtils.isEmpty(allUser)) {
            return new ResponseEntity<>(UserMapper.convertToModelUsers(allUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<User> updateUser(String userId, User userRequest) {
        com.management.studentattendancesystem.base.db.model.User dbUser = userRepository.findByIdAndStatus(Long.valueOf(userId), AMDILConstant.ACTIVE);

        if (null == dbUser) {
            return new ResponseEntity<>(userRequest, HttpStatus.NO_CONTENT);
        }

        List<com.management.studentattendancesystem.base.db.model.Role> dbRoleList = new ArrayList<>();

        for (Role role : userRequest.getRoles()) {
            com.management.studentattendancesystem.base.db.model.Role dbRole = new com.management.studentattendancesystem.base.db.model.Role(role.getName());
            dbRole.setId(Long.valueOf(role.getId()));
            dbRoleList.add(dbRole);
        }
        dbUser.setRoles(dbRoleList);
        userRepository.save(dbUser);
        return new ResponseEntity<>(userRequest, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUserById(String userId) {
        com.management.studentattendancesystem.base.db.model.User dbUser = userRepository.findByIdAndStatus(Long.valueOf(userId), AMDILConstant.ACTIVE);
        if (null == dbUser) {
            return new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(UserMapper.convertToModelUser(dbUser), HttpStatus.OK);
    }
}
