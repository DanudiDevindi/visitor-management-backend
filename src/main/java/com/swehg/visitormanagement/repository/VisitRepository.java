package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE :word % OR v.visitorEntity.nic LIKE :word % OR v.visitorEntity.mobile LIKE :word %)")
    Page<VisitEntity> getAllNotCheckOutByDateRange(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime < :currentDate) AND (v.checkinTime < :currentDate) AND CURRENT_TIMESTAMP >= :overdueDate AND (v.passCardEntity.name LIKE :word % OR v.visitorEntity.nic LIKE :word % OR v.visitorEntity.mobile LIKE :word %)")
    Page<VisitEntity> getAllOverdueCheckinByDateRange(@Param("word") String word, @Param("currentDate") Date CurrentDate, @Param("overdueDate") Date overdueDate, Pageable pageable);

}
