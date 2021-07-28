package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByNic(String nic);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.status<>:userStatus AND (:word IS NULL OR (u.nic LIKE %:word% OR u.mobile LIKE %:word% OR u.email LIKE %:word%))")
    List<UserEntity> getAllUsersExceptStatus(@Param("word") String word, @Param("userStatus") UserStatus userStatus);
}
