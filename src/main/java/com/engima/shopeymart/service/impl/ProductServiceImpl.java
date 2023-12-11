package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Product;
import com.engima.shopeymart.entity.ProductPrice;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.repository.ProductRepository;
import com.engima.shopeymart.service.ProductPriceService;
import com.engima.shopeymart.service.ProductService;
import com.engima.shopeymart.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceService productPriceService;
    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getProductId())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
        return getProductResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        Product currentProduct = new Product();
        Optional<Product> optionalProduct = productRepository.findById(currentProduct.getId());

        if (optionalProduct.isPresent()){
            Product product = Product.builder()
                    .name(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .build();
            productRepository.save(product);
            return getProductResponse(product);
        }else{
            return null;
        }
    }

    @Override
    public ProductResponse getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            return getProductResponse(product);
        }
        return null;
    }

//    @Override
//    public List<ProductResponse> getAll() {
//        List<Product> products = productRepository.findAll();
//        return products.stream().map(product -> ProductResponse.builder()
//                .id(product.getId())
//                .productName(product.getName())
//                .desc(product.getDescription())
//                .build())
//                .collect(Collectors.toList());
//    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    private static ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getName())
                .desc(product.getDescription())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest){

        Store store = storeService.getById(productRequest.getStoreId());

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(Store.builder()
                        .id(store.getId())
                        .build())
                .build();
        productPriceService.create(productPrice);
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .desc(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .build())
                .build();
    }
}
