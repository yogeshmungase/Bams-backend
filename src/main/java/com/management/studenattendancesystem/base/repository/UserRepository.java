package com.management.studenattendancesystem.base.repository;

import com.management.studenattendancesystem.base.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    @Query(value = "Select * from users where email = :email and status = 'Active' LIMIT 1", nativeQuery = true)
    Optional<User> findByEmailAndStatusActive(String email);

    List<User> findAllByStatus(String active);

    User findByIdAndStatus(Long id,String status);
}
