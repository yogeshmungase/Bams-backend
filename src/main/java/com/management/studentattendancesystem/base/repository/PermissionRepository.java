package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Permission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByStatus(String active);

    List<Permission> findAllByStatus(String active);

    Permission findByNameAndStatus(String name, String active);

    Permission findByName(String name);

    Permission findByIdAndStatus(Long permissionId,String status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE permissions set status= :status where id= :permissionId", nativeQuery = true)
    void updateStatusByPermissionId(@Param("permissionId") Long permissionId, @Param("status") String status);
}
