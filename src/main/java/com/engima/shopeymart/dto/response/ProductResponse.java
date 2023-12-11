package com.engima.shopeymart.dto.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String id;
    private String productName;
    private String desc;
    private long price;
    private Integer stock;
    private StoreResponse store;
}
