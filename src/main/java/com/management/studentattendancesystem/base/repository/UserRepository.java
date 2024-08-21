package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    @Query(value = "Select * from users where email = :email and status = 'Active' LIMIT 1", nativeQuery = true)
    Optional<User> findByEmailAndStatusActive(String email);
    @Query(value = "Select * from users where username = :username and status = 'Active' LIMIT 1", nativeQuery = true)
    Optional<User> findByUserNameAndStatusActive(String username);

    List<User> findAllByStatus(String active);

    User findByIdAndStatus(Long id,String status);

    List<User> findAllByStatusAndInstitutionId(String active,String institutionId);

    @Query(value = "SELECT COUNT (id) from users where institution_id= :institutionId and status = :status", nativeQuery = true)
    Integer getTotalUserAgainstInstitutionId(@Param("institutionId") String institutionId,@Param("status") String status);
}
