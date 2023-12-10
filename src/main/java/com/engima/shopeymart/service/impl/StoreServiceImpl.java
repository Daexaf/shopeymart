package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.StoreRequest;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.repository.StoreRepository;
import com.engima.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    //    @Override
//    public Store create(Store store) {
//        return storeRepository.save(store);
//    }
//
//    @Override
//    public Store getById(String id) {
//        return storeRepository.findById(id).orElse(null);//bisa pakai .get() atau .orElse(null);
//    }
//
    @Override
    public List<Store> getAllE() {
        return storeRepository.findAll();
    }
//
//    @Override
//    public Store update(Store store) {
//        Store currentStoreId = getById(store.getId());
//        if (currentStoreId != null){
//            return storeRepository.save(store);
//        }
//        return null;
//    }
//
//    @Override
//    public void delete(String id) {
//        Store currentStoreId = getById(id);
//        if (currentStoreId != null){
//            storeRepository.deleteById(id);
//        }
////        storeRepository.deleteById(id);
//    }

    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .build();
    }

    @Override
    public StoreResponse update(StoreRequest storeRequest) {
        StoreResponse getStore = getById(storeRequest.getId());

        if (getStore != null){
            Store updateStore = storeRepository.findById(storeRequest.getId()).orElse(null);

            if (updateStore != null){
                updateStore.setName(storeRequest.getName());
                updateStore.setNoSiup(storeRequest.getNoSiup());
                updateStore.setAddress(storeRequest.getAddress());
                updateStore.setMobilePhone(storeRequest.getMobilePhone());
                storeRepository.save(updateStore);

                return StoreResponse.builder()
                        .noSiup(updateStore.getNoSiup())
                        .storeName(updateStore.getName())
                        .build();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public StoreResponse getById(String id) {
        // Mengambil data dari repository menggunakan ID
        Optional<Store> storeOptional = storeRepository.findById(id);
        // Menangani kasus toko tidak ditemukan
        Store store = storeOptional.orElse(null);
        // Mengonversi objek Store menjadi objek StoreResponse
        assert store != null;
        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .build();
    }

    @Override
    public List<StoreResponse> getAll() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> storeResponses = new ArrayList<>();

        for (Store store : stores) {
            StoreResponse sr = new StoreResponse().toBuilder()
                    .noSiup(store.getNoSiup())
                    .storeName(store.getName())
                    .build();
            storeResponses.add(sr);
        }
        return storeResponses;
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }
}
