package com.engima.shopeymart.repository;

import com.engima.shopeymart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
