package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "error_log")
@Comment("失败记录表")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogEntity extends BaseEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    private long id;

    @Lob
    @Comment("推送事件溯源（主键）")
    private String event;

    @Lob
    @Comment("错误日志")
    private String errorContent;
}
