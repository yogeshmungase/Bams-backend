package com.management.studentattendancesystem.base.rest.mapper;

import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.db.model.User;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.service.impl.StudentServiceImpl;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import com.dox.ail.base.rest.model.Permission;
import com.dox.ail.base.rest.model.Role;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;


public class UserMapper {

    private static Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public static User convertToDBUser(com.dox.ail.base.rest.model.User userRequest) {

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setFirstName("");
        user.setLastName("");
        setUserRoles(user, userRequest);
        return user;
    }

    public static com.dox.ail.base.rest.model.User convertToModelUser(User dbUser) {

        com.dox.ail.base.rest.model.User user= new com.dox.ail.base.rest.model.User();
        user.setUsername(dbUser.getUsername());
        user.setPassword(dbUser.getPassword());
        user.setEmail(dbUser.getEmail());
        setModelUserRoles(user, dbUser);
        return user;
    }

    public static List<com.dox.ail.base.rest.model.User> convertToModelUsers(List<User> dbUsers) {

        List<com.dox.ail.base.rest.model.User> modelUsers = new ArrayList<>();
        for (User dbUser : dbUsers) {
            com.dox.ail.base.rest.model.User user = new com.dox.ail.base.rest.model.User();
            user.setId(String.valueOf(dbUser.getId()));
            user.setUsername(dbUser.getUsername());
            user.setPassword(dbUser.getPassword());
            user.setEmail(dbUser.getEmail());
           /* user.setCreatedAt(OffsetDateTime.from(dbUser.getCreatedAt()));
            user.setUpdatedAt(OffsetDateTime.from(dbUser.getUpdatedAt()));*/
            setModelUserRoles(user, dbUser);
            modelUsers.add(user);
        }
        return modelUsers;
    }

    private static void setModelUserRoles(com.dox.ail.base.rest.model.User modeluser, User dbUser) {

        Collection<com.management.studentattendancesystem.base.db.model.Role> dbRoles = dbUser.getRoles();
        List<com.dox.ail.base.rest.model.Role> modelRoles= new ArrayList<>();
        for (com.management.studentattendancesystem.base.db.model.Role dbRole : dbRoles) {
            com.dox.ail.base.rest.model.Role modelRole= new Role();
            modelRole.setId(String.valueOf(dbRole.getId()));
            modelRole.setName(dbRole.getName());
            setModelRolePermissions(modelRole,dbRole.getPermissions());
            modelRoles.add(modelRole);
        }
        modeluser.setRoles(modelRoles);
    }

    private static void setModelRolePermissions(Role modelRole, Collection<com.management.studentattendancesystem.base.db.model.Permission> permissions) {

        List<Permission> modelPermissions = new ArrayList<>();
        for (com.management.studentattendancesystem.base.db.model.Permission permission : permissions) {
            Permission modelPermission = new Permission();
            modelPermission.setId(String.valueOf(permission.getId()));
            modelPermission.setName(permission.getName());
            modelPermissions.add(modelPermission);
        }
        modelRole.setPermissions(modelPermissions);

    }


    private static void setUserRoles(User user, com.dox.ail.base.rest.model.User userRequest) {

        List<@Valid Role> roles = userRequest.getRoles();
        List<com.management.studentattendancesystem.base.db.model.Role> dbRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role requestRole : roles) {
                com.management.studentattendancesystem.base.db.model.Role dbRole = new com.management.studentattendancesystem.base.db.model.Role(requestRole.getName());
                dbRole.setId(Long.valueOf(requestRole.getId()));
                setRolePermissions(dbRole, requestRole.getPermissions());
                dbRoles.add(dbRole);
            }
        }
        user.setRoles(dbRoles);

    }

    private static void setRolePermissions(com.management.studentattendancesystem.base.db.model.Role dbRole, List<Permission> permissions) {
        List<com.management.studentattendancesystem.base.db.model.Permission> dbPermissions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission permission : permissions) {
                com.management.studentattendancesystem.base.db.model.Permission dbPermission = new com.management.studentattendancesystem.base.db.model.Permission();
                dbPermission.setId(Long.valueOf(permission.getId()));
                dbPermission.setName(permission.getName());
                dbPermissions.add(dbPermission);
            }
        }
        dbRole.setPermissions(dbPermissions);
    }

    public static com.management.studentattendancesystem.base.db.model.Permission convertToDBPermission(Permission permissionDto) {
        com.management.studentattendancesystem.base.db.model.Permission permission = new com.management.studentattendancesystem.base.db.model.Permission();
        permission.setName(permissionDto.getName());
        permission.setViewAccess(Constants.YES);
        permission.setAddAccess(Constants.YES);
        permission.setUpdateAccess(Constants.YES);
        permission.setDeleteAccess(Constants.YES);
        permission.setStatus(Constants.ACTIVE);
        return permission;
    }

    public static Permission convertToPermissionDto(com.management.studentattendancesystem.base.db.model.Permission dbPermission) {
        Permission permission = new Permission();
        permission.setName(dbPermission.getName());
        permission.setId(String.valueOf(dbPermission.getId()));
        return permission;
    }

    public static List<Permission> convertToPermissionDtos(List<com.management.studentattendancesystem.base.db.model.Permission> allPermissions) {
        List<Permission> permissionDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(allPermissions)) {
            for (com.management.studentattendancesystem.base.db.model.Permission dbPermission : allPermissions) {
                permissionDtos.add(convertToPermissionDto(dbPermission));
            }
        }

        return permissionDtos;
    }

    public static com.management.studentattendancesystem.base.db.model.Role convertToDBModelRole(Role roleDto) {

        com.management.studentattendancesystem.base.db.model.Role role = new com.management.studentattendancesystem.base.db.model.Role();
        role.setName(roleDto.getName());
        role.setStatus(Constants.ACTIVE);
        role.setPermissions(convertToDBPermissions(roleDto.getPermissions()));
        return role;
    }

    public static List<com.management.studentattendancesystem.base.db.model.Permission> convertToDBPermissions(List<Permission> permissionsDtos) {
        List<com.management.studentattendancesystem.base.db.model.Permission> dbPermissions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissionsDtos)) {
            for (Permission permissionDto : permissionsDtos) {
                com.management.studentattendancesystem.base.db.model.Permission permission = convertToDBPermission(permissionDto);
                permission.setId(Long.valueOf(permissionDto.getId()));
                dbPermissions.add(permission);
            }
        }
        return dbPermissions;

    }

    public static Role convertToRoleDto(com.management.studentattendancesystem.base.db.model.Role dbRole) {
        Role roleDto = new Role();
        roleDto.setId(String.valueOf(dbRole.getId()));
        roleDto.setName(dbRole.getName());
        roleDto.setPermissions(convertToPermissionDtos((List<com.management.studentattendancesystem.base.db.model.Permission>) dbRole.getPermissions()));
        return roleDto;

    }

    public static List<Role> convertToRoleDtos(List<com.management.studentattendancesystem.base.db.model.Role> dbRoles) {

        List<Role> rolesDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dbRoles)) {
            for (com.management.studentattendancesystem.base.db.model.Role dbRole : dbRoles) {
                Role role = convertToRoleDto(dbRole);
                rolesDtos.add(role);
            }
        }
        return rolesDtos;
    }

    public static List<StudentDTO> getStudentDTO(List<Student> studentList) {
        return null;
    }

    public static StudentThumbDetails getStudentThumbDetails(List<Student> studentList) {
        StudentThumbDetails studentThumbDetails = new StudentThumbDetails();

        List<StudentThumbProperties> studentThumbPropertiesList = new ArrayList<>();
        for (Student student : studentList) {
            StudentThumbProperties studentThumbProperties = new StudentThumbProperties();
            if (null != student.getThumb1()) {
                studentThumbProperties.setThumb1(Base64.getEncoder().encodeToString(student.getThumb1()));
            }
            if (null != student.getThumb2()) {
                studentThumbProperties.setThumb2(Base64.getEncoder().encodeToString(student.getThumb2()));
            }
            if (null != student.getThumb3()) {
                studentThumbProperties.setThumb3(Base64.getEncoder().encodeToString(student.getThumb3()));
            }
            if (null != student.getThumb4()) {
                studentThumbProperties.setThumb4(Base64.getEncoder().encodeToString(student.getThumb4()));
            }
            if (null != student.getThumb5()) {
                studentThumbProperties.setThumb5(Base64.getEncoder().encodeToString(student.getThumb5()));
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (!StringUtils.isEmpty(student.getFirstName())) {
                stringBuilder.append(student.getFirstName()).append(" ");
            }
            if (!StringUtils.isEmpty(student.getLastName())) {
                stringBuilder.append(student.getLastName()).append("-");
            }
            if (!StringUtils.isEmpty(student.getStudentAttendanceId())) {
                stringBuilder.append(student.getStudentAttendanceId());
            }
            studentThumbProperties.setNameWithAttendanceId(stringBuilder.toString());
            studentThumbPropertiesList.add(studentThumbProperties);
        }
        studentThumbDetails.setThumbPropertiesList(studentThumbPropertiesList);
        logger.info("studentThumbDetails : {}", studentThumbDetails);
        return studentThumbDetails;

    }
}
