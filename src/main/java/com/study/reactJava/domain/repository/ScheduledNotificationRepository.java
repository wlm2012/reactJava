package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.ScheduledNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledNotificationRepository extends JpaRepository<ScheduledNotificationEntity, Long> {

    List<ScheduledNotificationEntity> findByUpdateTimeAfter(LocalDateTime updateTimeAfter);

    List<ScheduledNotificationEntity> findByValid(String number);
}
