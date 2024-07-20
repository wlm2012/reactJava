package com.study.reactJava.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "ebook")
@Comment("电子书表")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EbookEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @Comment("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen")
    private Long id;

    @Comment("编号")
    @Column
    private String code;

    @Comment("书名")
    private String name;

}