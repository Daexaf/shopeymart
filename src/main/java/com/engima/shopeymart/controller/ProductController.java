package com.engima.shopeymart.controller;

import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.response.CommonResponse;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.entity.Product;
import com.engima.shopeymart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(productResponse)
                        .build());
    }

//    @GetMapping
//    public List<ProductResponse> getAllProduct(){
//        return productService.getAll();
//    }

    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAll();
    }
}
