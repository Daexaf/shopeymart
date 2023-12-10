package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse update(CustomerRequest customerRequest);

    void delete(String id);

    List<CustomerResponse> getAll();

    CustomerResponse getById(String id);


}
