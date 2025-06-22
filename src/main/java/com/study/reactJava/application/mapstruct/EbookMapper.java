package com.study.reactJava.application.mapstruct;

import com.study.reactJava.application.dto.request.EbookAddReq;
import com.study.reactJava.application.dto.response.EbookVO;
import com.study.reactJava.domain.entity.EbookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EbookMapper {

    EbookVO from(EbookEntity ebookEntity);

    List<EbookVO> from(List<EbookEntity> list);

    EbookEntity from(EbookAddReq ebookAddReq);
}
