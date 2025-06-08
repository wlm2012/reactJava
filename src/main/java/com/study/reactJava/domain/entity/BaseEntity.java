package com.study.reactJava.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(columnDefinition = "datetime not null default current_timestamp comment '创建时间'")
    private LocalDateTime createTime;

    @Column(columnDefinition = "datetime not null default current_timestamp on update current_timestamp comment '更新时间'")
    private LocalDateTime updateTime;
}
