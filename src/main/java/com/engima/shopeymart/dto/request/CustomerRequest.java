package com.engima.shopeymart.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerRequest {
    private String id;
    private String name;
    private String address;
    private String mobilePhone;
    private String email;
}
