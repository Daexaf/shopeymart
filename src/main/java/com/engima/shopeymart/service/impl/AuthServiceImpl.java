package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.constant.ERole;
import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.response.RegisterResponse;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.entity.Role;
import com.engima.shopeymart.entity.UserCredential;
import com.engima.shopeymart.repository.UserCredentialRepository;
import com.engima.shopeymart.service.AuthService;
import com.engima.shopeymart.service.CustomerService;
import com.engima.shopeymart.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try{
            //todo 1: set credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            //todo 2: set role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            roleService.getOrSave(role);
            //todo 3: set customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .build();
            customerService.createNewCustomer(customer);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }

    }
}