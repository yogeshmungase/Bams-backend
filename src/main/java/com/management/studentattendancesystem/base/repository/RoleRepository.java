package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE roles set status= :status where id= :roleId", nativeQuery = true)
    void updateStatusByRoleId(@Param("roleId") Long roleId, @Param("status") String status);

    Role findByIdAndStatus(Long roleId, String active);

    List<Role> findAllByStatus(String active);
}
