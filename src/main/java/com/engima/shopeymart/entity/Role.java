package com.engima.shopeymart.entity;

import com.engima.shopeymart.constant.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}
