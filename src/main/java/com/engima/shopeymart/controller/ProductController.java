package com.engima.shopeymart.controller;

import com.engima.shopeymart.constant.AppPath;
import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.response.CommonResponse;
import com.engima.shopeymart.dto.response.PagingResponse;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.entity.Product;
import com.engima.shopeymart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PRODUCT)
public class ProductController {

    private final ProductService productService;

    @PostMapping("/v1")
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

    @GetMapping(value = "/page")
    public ResponseEntity<?> getAllProductPage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
    ) {
        Page<ProductResponse> productResponses = productService.getAllByNameOrPrice(name, maxPrice, page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully get all product")
                        .data(productResponses.getContent())
                        .paging(pagingResponse)
                        .build());
    }
}
