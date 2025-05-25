package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.ScheduledConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduledConfigRepository extends JpaRepository<ScheduledConfigEntity, Long> {

    Optional<ScheduledConfigEntity> findByEvent(String event);
}
