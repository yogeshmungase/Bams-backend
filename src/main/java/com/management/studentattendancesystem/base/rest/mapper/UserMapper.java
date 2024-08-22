package com.management.studentattendancesystem.base.rest.mapper;

import com.management.studentattendancesystem.base.db.model.Batch;
import com.management.studentattendancesystem.base.db.model.Institution;
import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.db.model.User;
import com.management.studentattendancesystem.base.imgenhancer.GaborFilter;
import com.management.studentattendancesystem.base.rest.model.request.BatchDTO;
import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import com.management.studentattendancesystem.base.rest.model.request.StudentDTO;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import com.dox.ail.base.rest.model.Permission;
import com.dox.ail.base.rest.model.Role;
import jakarta.persistence.Tuple;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        user.setMobile(userRequest.getMobile());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setInstitutionId(userRequest.getInstitutionId());
        setUserRoles(user, userRequest);
        return user;
    }

    public static com.dox.ail.base.rest.model.User convertToModelUser(User dbUser) {

        com.dox.ail.base.rest.model.User user = new com.dox.ail.base.rest.model.User();
        user.setUsername(dbUser.getUsername());
        user.setPassword(dbUser.getPassword());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setMobile(dbUser.getMobile());
        user.setEmail(dbUser.getEmail());
        user.setStatus(dbUser.getStatus());
        user.setInstitutionId(dbUser.getInstitutionId());
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
            user.setFirstName(dbUser.getFirstName());
            user.setLastName(dbUser.getLastName());
            user.setPassword(dbUser.getPassword());
            user.setEmail(dbUser.getEmail());
            user.setMobile(dbUser.getMobile());
            user.setStatus(dbUser.getStatus());
           /* user.setCreatedAt(OffsetDateTime.from(dbUser.getCreatedAt()));
            user.setUpdatedAt(OffsetDateTime.from(dbUser.getUpdatedAt()));*/
            setModelUserRoles(user, dbUser);
            modelUsers.add(user);
        }
        return modelUsers;
    }

    private static void setModelUserRoles(com.dox.ail.base.rest.model.User modeluser, User dbUser) {

        Collection<com.management.studentattendancesystem.base.db.model.Role> dbRoles = dbUser.getRoles();
        List<com.dox.ail.base.rest.model.Role> modelRoles = new ArrayList<>();
        for (com.management.studentattendancesystem.base.db.model.Role dbRole : dbRoles) {
            com.dox.ail.base.rest.model.Role modelRole = new Role();
            modelRole.setId(String.valueOf(dbRole.getId()));
            modelRole.setName(dbRole.getName());
            setModelRolePermissions(modelRole, dbRole.getPermissions());
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
        if (!CollectionUtils.isEmpty(allPermissions)) {
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
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : studentList) {
            StudentDTO dto = new StudentDTO();

            dto.setStudentId(student.getId());
            dto.setBatchId(student.getBatchId());
            dto.setStudentAttendanceId(student.getStudentAttendanceId());
            dto.setAddress(student.getAddress());
            dto.setFirstName(student.getFirstName());
            dto.setMiddleName(student.getMiddleName());
            dto.setLastName(student.getLastName());
            dto.setMobile(student.getMobile());
            dto.setEmail(student.getEmail());

            if (null != student.getThumb1()) {
                dto.setThumb1(Base64.getEncoder().encodeToString(student.getThumb1()));
            }
            if (null != student.getThumb2()) {
                dto.setThumb2(Base64.getEncoder().encodeToString(student.getThumb2()));
            }
            if (null != student.getThumb3()) {
                dto.setThumb3(Base64.getEncoder().encodeToString(student.getThumb3()));
            }
            if (null != student.getThumb4()) {
                dto.setThumb4(Base64.getEncoder().encodeToString(student.getThumb4()));
            }
            if (null != student.getThumb5()) {
                dto.setThumb5(Base64.getEncoder().encodeToString(student.getThumb5()));
            }
            studentDTOS.add(dto);
        }

        return studentDTOS;
    }

    public static StudentThumbDetails getStudentThumbDetails(List<Student> studentList, String imageType) {
        StudentThumbDetails studentThumbDetails = new StudentThumbDetails();

        List<StudentThumbProperties> studentThumbPropertiesList = new ArrayList<>();
        for (Student student : studentList) {
            StudentThumbProperties studentThumbProperties = new StudentThumbProperties();
            if (null != student.getThumb1()) {
                if ("enhanced".equalsIgnoreCase(imageType)) {
                    studentThumbProperties.setThumb1(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb1())));
                } else {
                    studentThumbProperties.setThumb1(getBitMapBase64String(student.getThumb1()));
                }
            }
            if (null != student.getThumb2()) {
                if ("enhanced".equalsIgnoreCase(imageType)) {
                    studentThumbProperties.setThumb2(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb2())));
                } else {
                    studentThumbProperties.setThumb2(getBitMapBase64String(student.getThumb2()));
                }
            }
            if (null != student.getThumb3()) {
                if ("enhanced".equalsIgnoreCase(imageType)) {
                    studentThumbProperties.setThumb3(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb3())));
                } else {
                    studentThumbProperties.setThumb3(getBitMapBase64String(student.getThumb3()));
                }
            }
            if (null != student.getThumb4()) {
                if ("enhanced".equalsIgnoreCase(imageType)) {
                    studentThumbProperties.setThumb4(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb4())));
                } else {
                    studentThumbProperties.setThumb4(getBitMapBase64String(student.getThumb4()));
                }
            }
            if (null != student.getThumb5()) {
                if ("enhanced".equalsIgnoreCase(imageType)) {
                    studentThumbProperties.setThumb5(Base64.getEncoder().encodeToString(GaborFilter.enhanceImage(student.getThumb5())));
                } else {
                    studentThumbProperties.setThumb5(getBitMapBase64String(student.getThumb5()));
                }
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


    public static String getBitMapBase64String(byte[] bytes) {
        ByteArrayOutputStream baos = null;
        try {
            BufferedImage enhancedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            baos = new ByteArrayOutputStream();
            ImageIO.write(enhancedImage, "bmp", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            logger.error("Exception occur during to get getBitMapBase64String");
            return null;
        }
    }

    public static Batch mapBatchDetails(BatchDTO batchDTO) {
        Batch batch = new Batch();
        batch.setBatchName(batchDTO.getBatchName());
        batch.setEnabled(Boolean.TRUE.booleanValue());
        batch.setStartDate(batchDTO.getStartDate());
        batch.setEndDate(batchDTO.getEndDate());
        batch.setInstitutionId(batchDTO.getInstitutionId());
        logger.info("Inside mapBatchDetails() with details : {}", batchDTO);
        return batch;

    }

    public static InstitutionDTO getInstitutionDTO(Institution institution) {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setInstitutionName(institution.getInstitutionName());
        institutionDTO.setInstitutionId(institution.getInstitutionId());
        institutionDTO.setAllowedUser(institution.getAllowedUser());
        institutionDTO.setUserCreationAllowed(institution.isUserCreationAllowed());
        institutionDTO.setStatus(institution.getStatus());
        return institutionDTO;
    }

    public static Institution getInstitution(InstitutionDTO institutiondto) {
        Institution institution = new Institution();
        institution.setInstitutionName(institutiondto.getInstitutionName());
        institution.setInstitutionId(institutiondto.getInstitutionId());
        institution.setAllowedUser(institutiondto.getAllowedUser());
        institution.setUserCreationAllowed(institutiondto.isUserCreationAllowed());
        institution.setStatus(institutiondto.getStatus());
        return institution;
    }

    public static List<StudentDTO> getStudentDTOFromTuple(List<Tuple> allByBatchIdAndIsActive) {
        List<StudentDTO> studentDTOS = new ArrayList<>();

        try {
            for (Tuple tuple : allByBatchIdAndIsActive) {
                StudentDTO dto = new StudentDTO();
                dto.setStudentId(tuple.get(0, Long.class));
                dto.setStudentAttendanceId(tuple.get(1, String.class));
                dto.setBatchId(tuple.get(2, Long.class));
                dto.setFirstName(tuple.get(3, String.class));
                dto.setMiddleName(tuple.get(4, String.class));
                dto.setLastName(tuple.get(5, String.class));
                dto.setEmail(tuple.get(6, String.class));
                dto.setMobile(tuple.get(7, String.class));
                dto.setAddress(tuple.get(8, String.class));
                dto.setActive(tuple.get(9, Boolean.class));
                studentDTOS.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return studentDTOS;
    }
}

