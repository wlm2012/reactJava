package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "scheduled_config")
@Comment("推送控制表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledConfigEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    private long id;

    @Column(length = 20)
    @Comment("推送事件")
    private String event;

    @Column(length = 10)
    @Comment("重复推送控制（多少分钟内不重复推送））")
    private long time;
}