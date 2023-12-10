package com.engima.shopeymart.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StoreRequest {
    private String id;
    private String noSiup;
    private String name;
    private String address;
    private String mobilePhone;
}
