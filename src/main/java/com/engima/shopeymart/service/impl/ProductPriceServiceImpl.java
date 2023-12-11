package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.entity.ProductPrice;
import com.engima.shopeymart.repository.ProductPriceRepository;
import com.engima.shopeymart.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }
}
