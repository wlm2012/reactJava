package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scheduled_notification")
@Comment("定时通知表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledNotificationEntity extends BaseEntity{

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    private long id;

    @Comment("通知内容")
    private String content;


    @Comment("表达式")
    @Column(length = 20)
    private String expression;

    @Column(length = 1)
    @Comment("是否有效 1 有效；0 失效")
    private String valid;
}