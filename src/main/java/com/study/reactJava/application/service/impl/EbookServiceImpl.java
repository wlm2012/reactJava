package com.study.reactJava.application.service.impl;


import com.study.reactJava.domain.entity.EbookEntity;
import com.study.reactJava.domain.repository.EbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EbookServiceImpl {

    private final EbookRepository ebookRepository;


    public List<EbookEntity> findByIds(List<Long> ids) {
        List<EbookEntity> ebookEntities = ebookRepository.likeBooks("ally Lehne");
        System.out.println(ebookEntities.getFirst().getName());
        return ebookRepository.findByIds(ids);
    }

    @Async
    public void asyncFindByIds() {
        log.info(Thread.currentThread().isVirtual() + Thread.currentThread().getName());
        log.info(Thread.currentThread().toString());
    }

}
