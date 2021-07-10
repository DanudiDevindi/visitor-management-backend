package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    Optional<VisitorEntity> findByNic(String nic);

    @Query("SELECT v From VisitorEntity v WHERE v.mobile LIKE %:word% AND v.nic LIKE %:word% AND v.firstName LIKE %:word% AND v.lastName LIKE %:word%")
    Page<VisitorEntity> filterVisitor(@Param("word") String word, Pageable pageable);
}
