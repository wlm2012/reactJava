package com.study.reactJava.domain.entity;

import com.study.reactJava.common.config.snowflake.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user")
@Comment("用户表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @SnowflakeId
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    @Column(length = 19)
    private long id;

    @Column(length = 20)
    @Comment("用户名")
    private String username;

    @Column(length = 64)
    @Comment("密码")
    private String password;
}
