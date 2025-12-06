package com.quind.repository.adapter.jpa.repository;

import com.quind.repository.adapter.jpa.entity.DimensionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimensionsJpaRepository extends JpaRepository<DimensionsEntity, Long> {
}