package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.OrderRequest;
import com.engima.shopeymart.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createNewOrder(OrderRequest orderRequest);

    OrderResponse getOrderId(String id);

    List<OrderResponse> getAllOrder();


}
