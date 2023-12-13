package com.engima.shopeymart.repository;

import com.engima.shopeymart.constant.ERole;
import com.engima.shopeymart.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
