package com.study.reactJava.adapter.controller;


import com.study.reactJava.application.dto.request.IdsRequest;
import com.study.reactJava.application.service.impl.EbookServiceImpl;
import com.study.reactJava.domain.entity.EbookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/ebook")
@RequiredArgsConstructor
public class EbookController {

    private final EbookServiceImpl ebookService;

    @GetMapping("/findByIds")
    public List<EbookEntity> findByIds(IdsRequest idsRequest) {
        log.info("{}{}", Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        log.info(Thread.currentThread().toString());

        ebookService.asyncFindByIds();
        return ebookService.findByIds(idsRequest.ids());
    }

    @PostMapping("/addFakeData")
    public void addFakeData() {
        log.info("begin");
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        log.info("{}{}", Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        for (int i = 0; i < 300; i++) {
            completableFutures.add(ebookService.addFakeData(i));
        }
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new)).join();
        log.info("end");
    }
}
