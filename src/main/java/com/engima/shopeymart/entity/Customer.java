package com.engima.shopeymart.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name="m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "full_name", nullable = false, length = 100)
    private String name;
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Column(name = "mobile_phone", unique = true, nullable = false, length = 15)
    private String mobilePhone;
    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
    //id
    //name
    //address
    //mobilePhone -> unique
    //email ->

    //bikin crud pakai ddo
}
