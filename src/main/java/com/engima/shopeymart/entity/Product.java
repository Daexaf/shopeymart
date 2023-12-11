package com.engima.shopeymart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name="m_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    @OneToMany(mappedBy = "product")
    private List<ProductPrice> productPrices;

}
