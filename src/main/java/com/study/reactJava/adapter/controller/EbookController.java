package com.study.reactJava.adapter.controller;


import com.study.reactJava.application.dto.request.EbookAddReq;
import com.study.reactJava.application.dto.request.IdsReq;
import com.study.reactJava.application.dto.response.EbookVO;
import com.study.reactJava.application.service.impl.EbookServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<EbookVO> findByIds(IdsReq idsReq) {
        log.info("{}{}", Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        log.info(Thread.currentThread().toString());

        ebookService.asyncFindByIds();
        return ebookService.findByIds(idsReq.ids());
    }

    @PostMapping("/addFakeData")
    public void addFakeData() {
        log.info("begin");
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        log.info("{}{}", Thread.currentThread().isVirtual(), Thread.currentThread().getName());
        for (int i = 0; i < 3000; i++) {
            completableFutures.add(ebookService.addFakeData(i));
        }
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new)).join();
        log.info("end");
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody EbookAddReq ebookAddReq) {
        ebookService.addEbook(ebookAddReq);
    }
}
