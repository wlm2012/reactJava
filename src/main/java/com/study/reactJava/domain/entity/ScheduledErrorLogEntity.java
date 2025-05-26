package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_error_log")
@Comment("推送记录表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledErrorLogEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    @Column(length = 19)
    private long id;

    @Column(length = 30)
    @Comment("推送事件")
    private String event;

    @Column(length = 1)
    @Comment("是否推送成功")
    private String pushSuccess;

    @Comment("推送次数")
    private int pushTime;

    @CreatedDate
    @Comment("创建时间")
    private LocalDateTime createTime;


}
