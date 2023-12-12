package com.engima.shopeymart.repository;

import com.engima.shopeymart.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {

    //query method
    Optional<ProductPrice> findByProduct_IdAndIsActive(String product_id, Boolean active);
}
