package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "ebook")
@Comment("电子书表")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EbookEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    @Column(length = 19)
    private long id;

    @Comment("编号")
    @Column
    private String code;

    @Comment("书名")
    private String name;

}