package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.constant.ERole;
import com.engima.shopeymart.dto.request.AuthRequest;
import com.engima.shopeymart.dto.response.LoginResponse;
import com.engima.shopeymart.dto.response.RegisterResponse;
import com.engima.shopeymart.entity.AppUser;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.entity.Role;
import com.engima.shopeymart.entity.UserCredential;
import com.engima.shopeymart.repository.UserCredentialRepository;
import com.engima.shopeymart.security.JwtUtil;
import com.engima.shopeymart.service.AuthService;
import com.engima.shopeymart.service.CustomerService;
import com.engima.shopeymart.service.RoleService;
import com.engima.shopeymart.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            validationUtil.validate(request);
            //todo 2: set role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            Role roleSaved = roleService.getOrSave(role);
            //todo 1: set credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(roleSaved)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //todo 3: set customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .name(request.getCustomerName())
                    .address(request.getAddress())
                    .mobilePhone(request.getMobilePhone())
                    .email(request.getEmail())
                    .build();
            customerService.createNewCustomer(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        // tempat untuk melakukan logic dan validasi login
        validationUtil.validate(authRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase(),
                authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //object appUser
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}