package com.engima.shopeymart.entity;
import com.engima.shopeymart.dto.response.StoreResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name="m_store")
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="no_siup", unique = true, nullable = false, length = 30)
    private String noSiup;
    @Column(name="name", nullable = false, length = 100)
    private String name;
    @Column(name="address", nullable = false, length = 100)
    private String address;
    @Column(name="mobile_phone", unique = true, nullable = false, length = 30)
    private String mobilePhone;
}
