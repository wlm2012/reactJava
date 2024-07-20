package com.study.reactJava.adapter.controller;


import com.study.reactJava.application.dto.request.IdsRequest;
import com.study.reactJava.application.service.impl.EbookServiceImpl;
import com.study.reactJava.domain.entity.EbookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
