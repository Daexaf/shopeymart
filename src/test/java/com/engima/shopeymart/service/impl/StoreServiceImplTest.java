package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.StoreRequest;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.repository.StoreRepository;
import com.engima.shopeymart.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServiceImplTest {

    //mock repository
    private final StoreRepository storeRepository = mock(StoreRepository.class);

    //create StoreService Instance
    private final StoreService storeService = new StoreServiceImpl(storeRepository);

//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        storeService = new StoreServiceImpl(storeRepository);
//    }

    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        Store dummyStore = new Store();
        dummyStore.setId("123");
        dummyStore.setName("toko dummy");

        //ini kalo pake req, res
//        StoreRequest dummyStoreRequest = new StoreRequest();
//        dummyStoreRequest.setId("123");
//        dummyStoreRequest.setName("toko dummy");

        //ini kalo pake req, res
//        StoreResponse dummyStoreResponse = storeService.create(dummyStoreRequest);
        // verivy(storeRepository).save(any(Store.class));
        // assertEquals(dummystoreRequest.getName(), dummyStoreResponse
        // .getStoreName()
        // )

        //mock behaviour repository -> save
        when(storeRepository.save(any(Store.class))).thenReturn(dummyStore);

        //perform then create operation
        Store createStore = storeService.create(dummyStore);

        //verify Repository
        verify(storeRepository, times(1)).save(dummyStore);

        //verif / assert
        assertEquals(dummyStore.getId(), createStore.getId());
        assertEquals(dummyStore.getName(), createStore.getName());
    }

//    @Test
//    void itShouldGetAllDataStoreWhenCallGetAll(){
//        List<Store> dummyStore = new ArrayList<>();
//        dummyStore.add(new Store("1", "123", "Berkah Ibu", "ragunan", "09875739423"));
//        dummyStore.add(new Store("2", "124", "Berkah Ayah", "ragunan", "0983983435"));
//        dummyStore.add(new Store("3", "125", "Berkah Kakak", "ragunan", "438983902324"));
//
//        when(storeRepository.findAll()).thenReturn(dummyStore);
//        List<Store> retriveStore = storeService.getAllE();
//
//        assertEquals(dummyStore.size(), retriveStore.size());
//        for (int i = 0; i < dummyStore.size(); i++) {
//            assertEquals(dummyStore.get(i).getName(), retriveStore.get(i).getName());
//        }
//
//    }

    //ini kalau pakai storeResponse untuk get all;
    @Test
    void itShouldGetAllDataStoreResponseWhenCallGetAll(){
        List<Store> dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1", "123", "Berkah Ibu", "ragunan", "09875739423"));
        dummyStore.add(new Store("2", "124", "Berkah Ayah", "ragunan", "0983983435"));
        dummyStore.add(new Store("3", "125", "Berkah Kakak", "ragunan", "438983902324"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<StoreResponse> retriveStore = storeService.getAll();

        assertEquals(dummyStore.size(), retriveStore.size());
        for (int i = 0; i < dummyStore.size(); i++) {
            assertEquals(dummyStore.get(i).getName(), retriveStore.get(i).getStoreName());
        }
    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore(){
        String storeId = "1";
        Store store = new Store("1", "0348985", "Berkah Selalu", "ragunan", "00934859");
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        StoreResponse storeResponse = storeService.getByIdE(storeId);

        verify(storeRepository).findById(storeId);
        assertNotNull(storeResponse);
        assertEquals(storeId, storeResponse.getId());
        assertEquals("Berkah Selalu", storeResponse.getStoreName());
    }

    @Test
    void itShouldUpdateDataWhenUpdateByIdStore(){
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("2");
        dummyStoreRequest.setName("jaya jaya");
        dummyStoreRequest.setNoSiup("0921da");
        dummyStoreRequest.setAddress("bogor");
        dummyStoreRequest.setMobilePhone("0981787324");

        Store firstStore = new Store("1", "0348985", "Berkah Selalu", "ragunan", "00934859");

        when(storeRepository.findById(dummyStoreRequest.getId())).thenReturn(Optional.of(firstStore));

        StoreResponse storeResponse = storeService.update(dummyStoreRequest);

        verify(storeRepository, times(1)).findById(dummyStoreRequest.getId());

        verify(storeRepository, times(1)).save(any(Store.class));

        assertNotNull(storeResponse);
        assertEquals(dummyStoreRequest.getId(), storeResponse.getId());
        assertEquals(dummyStoreRequest.getName(), storeResponse.getStoreName());
        assertEquals(dummyStoreRequest.getNoSiup(), storeResponse.getNoSiup());
    }

    @Test
    void deletedById(){
        String id = "1";
        storeService.delete(id);
        verify(storeRepository, times(1)).deleteById(id);
    }

}