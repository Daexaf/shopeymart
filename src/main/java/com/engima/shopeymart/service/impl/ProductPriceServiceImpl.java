package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.entity.ProductPrice;
import com.engima.shopeymart.repository.ProductPriceRepository;
import com.engima.shopeymart.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }

    @Override
    public ProductPrice getById(String id) {
        return productPriceRepository.findById(id).orElseThrow();
    }

    @Override
    public ProductPrice findProductPriceIsActive(String productId, Boolean active) {
        return productPriceRepository.findByProduct_IdAndIsActive(productId,active).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Product not found"));
    }
}
