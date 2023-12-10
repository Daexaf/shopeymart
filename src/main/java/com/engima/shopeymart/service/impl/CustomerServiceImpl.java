package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.repository.CustomerRepository;
import com.engima.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return CustomerResponse.builder()
                .fullName(customer.getName())
                .phone(customer.getMobilePhone())
                .address(customer.getAddress())
                .build();
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

                return CustomerResponse.builder()
                        .fullName(updateCustomer.getName())
                        .address(updateCustomer.getAddress())
                        .phone(updateCustomer.getMobilePhone())
                        .build();
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
        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (Customer customer : customers){
            CustomerResponse cr = new CustomerResponse().toBuilder()
                    .fullName(customer.getName())
                    .phone(customer.getMobilePhone())
                    .address(customer.getAddress())
                    .build();
            customerResponses.add(cr);
        }
        return customerResponses;
    }

    @Override
    public CustomerResponse getById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerOptional.orElse(null);
        assert customer != null;
        return CustomerResponse.builder()
                .fullName(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getMobilePhone())
                .build();
    }
}
