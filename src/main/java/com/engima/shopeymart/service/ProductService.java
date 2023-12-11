package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProductAndProductPrice(ProductRequest productRequest);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    ProductResponse getById(String id);

//    List<ProductResponse> getAll();

    List<Product> getAll();

    void delete(String id);

}
