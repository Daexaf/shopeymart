package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.request.StoreRequest;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Product;
import com.engima.shopeymart.entity.ProductPrice;
import com.engima.shopeymart.repository.ProductRepository;
import com.engima.shopeymart.service.ProductPriceService;
import com.engima.shopeymart.service.ProductService;
import com.engima.shopeymart.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final StoreService storeService = mock(StoreService.class);
    private final ProductPriceService productPriceService = mock(ProductPriceService.class);
    private final ProductService productService = new ProductServiceImpl(productRepository,storeService,productPriceService);

    @BeforeEach
    public void setUp(){
        //reset mock behaviour
        reset(productRepository, storeService, productPriceService);
    }

    @Test
    void createProductAndProductPrice(){
        //store request
        StoreResponse dummyStore = new StoreResponse();
        dummyStore.setId("store1");
        dummyStore.setStoreName("Berkah Selalu");
        dummyStore.setNoSiup("12324345");

        when(storeService.getByIdE(anyString())).thenReturn(dummyStore);

        //mock product that will be save
        Product saveProduct = new Product();
        saveProduct.setId("productId");
        saveProduct.setName("Oreo");
        saveProduct.setDescription("Hitam hitam enak");
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(saveProduct);

        //data dummy request
        ProductRequest dummyRequest = mock(ProductRequest.class);
        when(dummyRequest.getStoreId()).thenReturn(StoreResponse.builder()
                        .id("storeId")
                .build());
        when(dummyRequest.getProductName()).thenReturn(saveProduct.getName());
        when(dummyRequest.getDescription()).thenReturn(saveProduct.getDescription());
        when(dummyRequest.getPrice()).thenReturn(100000L);
        when(dummyRequest.getStock()).thenReturn(10);

        //call method create
        ProductResponse productResponse = productService.createProductAndProductPrice(dummyRequest);

        //validate response
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName(), productResponse.getProductName());

        //validate that the product price was set correct
        assertEquals(dummyRequest.getPrice(), productResponse.getPrice());
        assertEquals(dummyRequest.getStock(), productResponse.getStock());

        //validate interaction with store
        assertEquals(dummyStore.getId(), productResponse.getStore().getId());

        //verify interaction with mock object
        verify(storeService).getByIdE(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));

    }

}