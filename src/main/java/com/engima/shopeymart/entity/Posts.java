package com.engima.shopeymart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "m_posts")
public class Posts {

    @Id
    private Long id;
    private String title;
    private String body;
    @Column(name = "user_id")
    private Integer userId;
}
