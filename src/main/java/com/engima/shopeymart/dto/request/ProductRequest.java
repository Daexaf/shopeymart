package com.engima.shopeymart.dto.request;

import com.engima.shopeymart.dto.response.StoreResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
    private String productId;
    @NotBlank(message = "Product name is required")
    private String productName;
    @NotBlank(message = "product description is required")
    private String description;
    @NotBlank(message = "product price is required")
    @Min(value = 0, message = "price must be greater than equal 0")
    private Long price;
    @NotBlank(message = "product stock is required")
    @Min(value = 0, message = "stock must be greater than 0")
    private Integer stock;
    @NotBlank(message = "storeId is required")
    private String storeId;
}
