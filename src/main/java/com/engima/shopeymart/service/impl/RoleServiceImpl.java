package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.entity.Role;
import com.engima.shopeymart.repository.RoleRepository;
import com.engima.shopeymart.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optionalRole = repository.findByName(role.getName());
        //cek ada di db pakai get
        if (!optionalRole.isEmpty()){
            return optionalRole.get();
        }
        //jika tidak ada create baru
        return repository.save(role);
    }
}
