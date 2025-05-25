package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.ScheduledLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduledLogRepository extends JpaRepository<ScheduledLogEntity, Long> {

    Optional<ScheduledLogEntity> findTopByEventOrderByTimeDesc(String event);
}
