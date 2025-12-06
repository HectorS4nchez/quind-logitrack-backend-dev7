package com.quind.repository.adapter.jpa.repository;

import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationHistoryJpaRepository extends JpaRepository<LocationHistoryEntity, Long> {

    List<LocationHistoryEntity> findByCity(String city);

    List<LocationHistoryEntity> findByCountry(String country);

    @Query("SELECT lh FROM LocationHistoryEntity lh WHERE lh.timestamp BETWEEN :start AND :end")
    List<LocationHistoryEntity> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}