package com.engima.shopeymart.controller;

import com.engima.shopeymart.constant.AppPath;
import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/v1")
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.create(customerRequest);
    }

    @GetMapping("/v1")
    public List<CustomerResponse> getCustomer(){
        return customerService.getAll();
    }

    @GetMapping("/v1/{id}")
    public CustomerResponse getIdCustomer(@PathVariable String id){
        return customerService.getById(id);
    }

    @DeleteMapping("/v1/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(@PathVariable String id){
        customerService.delete(id);
    }

    @PutMapping("/v1")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.update(customerRequest);
    }
}
