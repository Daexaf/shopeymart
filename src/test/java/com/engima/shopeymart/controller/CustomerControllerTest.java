package com.engima.shopeymart.controller;

import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
        //inisialisasi mock dan controller
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponse = new CustomerResponse();

        when(customerService.create(customerRequest)).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerController.createCustomer(customerRequest);

        assertEquals(customerResponse,actualResponse);
    }

    @Test
    void getCustomer() {
        List<CustomerResponse> expectedResponse = new ArrayList<>();

        when(customerService.getAll()).thenReturn(expectedResponse);

        List<CustomerResponse> actualResponse = customerController.getCustomer();

        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    void getIdCustomer() {
        String customerId = "1";
        CustomerResponse expectedResponse = new CustomerResponse();

        when(customerService.getById(customerId)).thenReturn(expectedResponse);
        CustomerResponse actualResponse = customerController.getIdCustomer(customerId);

        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    void deleteCustomer() {
        String customerId = "1";
//        ResponseEntity<?> responseEntity = customerController.deleteCustomer(customerId);
        customerController.deleteCustomer(customerId);
        verify(customerService,times(1)).delete(customerId);
    }

    @Test
    void updateCustomer() {
        CustomerResponse expectedResponse = new CustomerResponse();
        CustomerRequest customerRequest = new CustomerRequest();

        when(customerService.update(customerRequest)).thenReturn(expectedResponse);

        CustomerResponse actualResponse = customerController.updateCustomer(customerRequest);

        assertEquals(expectedResponse, actualResponse);
    }
}