package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.OrderDetailRequest;
import com.engima.shopeymart.dto.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {

    OrderDetailResponse create(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse getDetaiOrderId(String id);

    List<OrderDetailResponse> getAllOrderDetail();
}
