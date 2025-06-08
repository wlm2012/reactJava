package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogEntityRepository extends JpaRepository<ErrorLogEntity, Long> {
}
