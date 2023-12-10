package com.engima.shopeymart.controller;

import com.engima.shopeymart.constant.AppPath;
import com.engima.shopeymart.dto.request.StoreRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.service.StoreService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {
    private final StoreService storeService;

//    @PostMapping
//    public Store createStore(@RequestBody Store store){
//        System.out.println("Store Add Successful");
//        return storeService.create(store);
//    }
    @GetMapping
    public List<Store> getAlle(){
        System.out.println("Successful get all data");
        return storeService.getAllE();
    }
//
////    ini yang ga pake id
//    @PutMapping
//    public Store updateStore(@RequestBody Store store){
//        return storeService.update(store);
//    }
//
////    //ini pake id
////    @PutMapping(value="/store/{id}")
////    public Store updateStore(@PathVariable String id, @RequestBody Store store){
////        store.setId(id);
////        System.out.println("Successful update data");
////        return storeService.update(store);
////    }
//    @DeleteMapping(value="/{id}")
//    public void deleteStore(@PathVariable String id){
//        System.out.println("Successful delete data");
//        storeService.delete(id);
//    }
//    @GetMapping(value = "/{id}")
//    public Store getById(@PathVariable String id){
//        System.out.println("Successful get data by id");
//        return storeService.getById(id);
//    }
    @PostMapping("/v1")
    public StoreResponse createStores(@RequestBody StoreRequest storeRequest){
        return storeService.create(storeRequest);
    }

    @GetMapping("/v1/{id}")
    public StoreResponse getIdStores(@PathVariable String id){
        return storeService.getById(id);
    }

    @GetMapping("/v1")
    public List<StoreResponse> getCustomer(){
        return storeService.getAll();
    }

    @DeleteMapping("/v1/{id}")
    public void deleteCustomer(@PathVariable String id){
        storeService.delete(id);
    }
}
