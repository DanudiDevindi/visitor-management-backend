package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.EmployeeEntity;
import com.swehg.visitormanagement.enums.EmployeeStatus;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByNic(String nic);
    Optional<EmployeeEntity> findByMobile(String mobile);
    Optional<EmployeeEntity> findByEmail(String email);
    @Query("SELECT e from EmployeeEntity e WHERE e.nic LIKE %:word% OR e.mobile LIKE %:word% OR e.email LIKE %:word%")
    Page<EmployeeEntity> getAllEmployee(@Param("word")String word, Pageable pageable);
    @Query("SELECT e from EmployeeEntity e WHERE e.status=:status")
    List<EmployeeEntity> getAllEmployeeForSearch(@Param("status")EmployeeStatus status);
}
