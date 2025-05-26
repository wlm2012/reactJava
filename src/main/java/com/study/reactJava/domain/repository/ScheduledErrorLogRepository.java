package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.ScheduledErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledErrorLogRepository extends JpaRepository<ScheduledErrorLogEntity, Long> {

    List<ScheduledErrorLogEntity> findByPushSuccessAndPushTimeLessThan(String pushSuccess, int pushTimeIsLessThan);
}
