package com.study.reactJava.application.service.impl;


import com.study.reactJava.application.dto.response.EbookVO;
import com.study.reactJava.application.mapstruct.EbookMapper;
import com.study.reactJava.domain.entity.EbookEntity;
import com.study.reactJava.domain.repository.EbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class EbookServiceImpl {

    private final EbookRepository ebookRepository;

    private final EbookMapper ebookMapper;


    public List<EbookVO> findByIds(List<Long> ids) {
        return ebookMapper.from(ebookRepository.findByIds(ids));
    }

    @Async
    public void asyncFindByIds() {
        log.info("{}{}", Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        log.info(Thread.currentThread().toString());
    }

    @Async
    public CompletableFuture<Void> addFakeData(int j) {
        log.info("begin:{},thread: {},{}", j, Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        Faker faker = new Faker();
        List<EbookEntity> ebookEntities = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ebookEntities.add(EbookEntity.builder()
                    .code(faker.code().gtin13())
                    .name(faker.name().fullName())
                    .build());
        }
        ebookRepository.saveAll(ebookEntities);
        return CompletableFuture.completedFuture(null);
    }
}
