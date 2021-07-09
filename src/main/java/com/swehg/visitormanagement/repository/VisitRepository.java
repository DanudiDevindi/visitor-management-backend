package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime BETWEEN :startDate AND :endDate)")
    Page<VisitEntity> getAllNotCheckOutByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

}
