package com.engima.shopeymart.service;

import com.engima.shopeymart.dto.request.StoreRequest;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Store;

import java.util.List;

public interface StoreService {
//    Store create(Store store);
//    Store getById(String id);
    List<Store> getAllE();
//    Store update(Store store);
//    void delete(String id);

    StoreResponse create(StoreRequest storeRequest);

    StoreResponse getById(String id);

    List<StoreResponse> getAll();

    void delete(String id);

    StoreResponse update(StoreRequest storeRequest);

}
