package com.engima.shopeymart.repository;

import com.engima.shopeymart.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,String> {
//    kalo pake query native
//    public store getAll(){
//        query
//    }

    //query method yaitu query kompleks tambahan
}
