package com.quind.repository.adapter.jpa.repository;

import com.quind.repository.adapter.jpa.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageJpaRepository extends JpaRepository<PackageEntity, String> {
}