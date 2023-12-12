package com.engima.shopeymart.controller;

import com.engima.shopeymart.constant.AppPath;
import com.engima.shopeymart.dto.request.OrderRequest;
import com.engima.shopeymart.dto.response.CommonResponse;
import com.engima.shopeymart.dto.response.OrderResponse;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ORDER)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1")
    public ResponseEntity<?> createNew(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.createNewOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<OrderResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully created new product")
                .data(orderResponse)
                .build());
    }
}
