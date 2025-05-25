package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_log")
@Comment("推送记录表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledLogEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    @Column(length = 19)
    private long id;

    @Column(length = 20)
    @Comment("推送事件")
    private String event;

    @Comment("推送时间")
    private LocalDateTime time;
}
