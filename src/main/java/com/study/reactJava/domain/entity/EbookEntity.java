package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ebook")
@Comment("电子书表")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EbookEntity extends BaseEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    private long id;

    @Comment("编号")
    @Column
    private String code;

    @Comment("书名")
    private String name;

}