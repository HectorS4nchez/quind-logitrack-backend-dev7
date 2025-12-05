package com.quind.repository.adapter.jpa.repository;

import com.quind.repository.adapter.jpa.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientJpaRepository extends JpaRepository<RecipientEntity, Long> {

    List<RecipientEntity> findByName(String name);
}