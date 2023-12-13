package com.engima.shopeymart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_admin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
}
