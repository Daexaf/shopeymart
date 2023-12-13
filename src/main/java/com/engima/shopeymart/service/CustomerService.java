package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse createNewCustomer(Customer request);

    CustomerResponse update(CustomerRequest customerRequest);

    void delete(String id);

    List<CustomerResponse> getAll();

    CustomerResponse getById(String id);


}
