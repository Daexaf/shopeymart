package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.repository.CustomerRepository;
import com.engima.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .mobilePhone(customerRequest.getMobilePhone())
                .email(customerRequest.getEmail())
                .build();
        customerRepository.save(customer);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        CustomerResponse getCustomer = getById(customerRequest.getId());

        if (getCustomer != null){
            Customer updateCustomer = customerRepository.findById(customerRequest.getId()).orElse(null);
            if (updateCustomer != null){
                updateCustomer.setName(customerRequest.getName());
                updateCustomer.setAddress(customerRequest.getAddress());
                updateCustomer.setEmail(customerRequest.getEmail());
                updateCustomer.setMobilePhone(customerRequest.getMobilePhone());
                customerRepository.save(updateCustomer);

                return getCustomerResponse(updateCustomer);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::getCustomerResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .fullName(customer.getName())
                    .address(customer.getAddress())
                    .phone(customer.getMobilePhone())
                    .build();
        }
        return null;
    }
//    public CustomerResponse getById(String id) {
//        Customer customers = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("ID not found"));
//        return  getCustomerResponse(customers);
//    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getName())
                .phone(customer.getMobilePhone())
                .address(customer.getAddress())
                .build();
    }
}
