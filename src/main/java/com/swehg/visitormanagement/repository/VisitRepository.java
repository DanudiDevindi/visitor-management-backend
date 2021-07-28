package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitEntity;
import com.swehg.visitormanagement.entity.VisitorEntity;
import com.swehg.visitormanagement.enums.PassCardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    Page<VisitEntity> getAllNotCheckOutByDateRange(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime < :currentDate) AND (v.checkinTime < :currentDate) AND CURRENT_TIMESTAMP >= :overdueDate AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    Page<VisitEntity> getAllOverdueCheckinByDateRange(@Param("word") String word, @Param("currentDate") Date CurrentDate, @Param("overdueDate") Date overdueDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE (:startDate IS NULL OR v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    Page<VisitEntity> getAllVisitHistory(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (:startDate IS NULL OR v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    Page<VisitEntity> getAllNotCheckOutVisitHistory(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE v.checkoutTime IS NOT NULL AND (:startDate IS NULL OR v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    Page<VisitEntity> getAllCheckedOutVisitHistory(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT v FROM VisitEntity v WHERE v.visitorEntity = :visitor AND v.passCardEntity.status=:passCardStatus")
    List<VisitEntity> checkVisitorVisit(@Param("visitor")VisitorEntity visitorEntity, @Param("passCardStatus")PassCardStatus passCardStatus);

    //--------------- counts -----------------

    @Query("SELECT COUNT(v) FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime BETWEEN :startDate AND :endDate) AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    int getAllNotCheckOutByDateRangeCount(@Param("word") String word, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(v) FROM VisitEntity v WHERE v.checkoutTime IS NULL AND (v.checkinTime < :currentDate) AND (v.checkinTime < :currentDate) AND CURRENT_TIMESTAMP >= :overdueDate AND (v.passCardEntity.name LIKE %:word% OR v.visitorEntity.nic LIKE %:word% OR v.visitorEntity.mobile LIKE %:word%)")
    int getAllOverdueCheckinByDateRangeCount(@Param("word") String word, @Param("currentDate") Date CurrentDate, @Param("overdueDate") Date overdueDate);

    @Query("SELECT COUNT(v) FROM VisitEntity v WHERE v.checkinTime BETWEEN :currentDate AND :overdueDate")
    int getAllVisitCount(@Param("currentDate") Date CurrentDate, @Param("overdueDate") Date overdueDate);


}
