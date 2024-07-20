package com.study.reactJava.domain.repository;

import com.study.reactJava.domain.entity.EbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EbookRepository extends JpaRepository<EbookEntity, Long> {

    @Query(value = """
            select e from EbookEntity e where
            (e.id in :ids or :ids is null)""")
    List<EbookEntity> findByIds(List<Long> ids);

    @Query(value = """
            select e from EbookEntity e where e.name like %:name%""")
    List<EbookEntity> likeBooks(String name);

}