package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.ProductRequest;
import com.engima.shopeymart.dto.response.ProductResponse;
import com.engima.shopeymart.dto.response.StoreResponse;
import com.engima.shopeymart.entity.Product;
import com.engima.shopeymart.entity.ProductPrice;
import com.engima.shopeymart.entity.Store;
import com.engima.shopeymart.repository.ProductPriceRepository;
import com.engima.shopeymart.repository.ProductRepository;
import com.engima.shopeymart.service.ProductPriceService;
import com.engima.shopeymart.service.ProductService;
import com.engima.shopeymart.service.StoreService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (optionalProduct.isPresent()) {
            Product product = Product.builder()
                    .name(productRequest.getProductName())
                    .description(productRequest.getDescription())
                    .build();
            productRepository.save(product);
            return getProductResponse(product);
        } else {
            return null;
        }
    }

    @Override
    public ProductResponse getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
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
////                        .price(product.getProductPrices().stream().map(productPrice -> productPrice.toBuilder()
////                                        .price(productPrice.getPrice())
////                                        .stock(productPrice.getStock())
////                                        .isActive(productPrice.isActive())
////                                        .build())
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
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {

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

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        //spesification digunakan untuk menentukan kriteria pencarian, disini kriteria pencarian ditandai dengan root, root yang dimaksud adalah entity product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            //join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPrices = root.join("productPrices");
            //predicate digunakan untuk menggunakan LIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
            //disini akan mencari nama product atau harga yang sama atau harga dibawahnya, makanya menggunakan lessthanorequalto
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            }
            //kode return untuk mengembalikkan query dimana pada dasarnya kita membanngun klausa where yang sudah ditentukan dari predicate atau kriteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(specification, pageable);
        //tempat menyimpan respon product
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            //untuk looping data products yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrices()
                    .stream()
                    .filter(ProductPrice::isActive).findFirst();
            if (productPrice.isEmpty()) continue;
            { //cek ada price atau tidak, kalau ga ada lewati
                Store store = productPrice.get().getStore(); //digunakan jika harga product yang aktif ditemukan di store
                productResponses.add(ProductResponse.builder()
                        .id(product.getId())
                        .productName(product.getName())
                        .desc(product.getDescription())
                        .price(productPrice.get().getPrice())
                        .stock(productPrice.get().getStock())
                        .store(StoreResponse.builder()
                                .id(store.getId())
                                .storeName(store.getName())
                                .noSiup(store.getNoSiup())
                                .build())
                        .build());
            }
            // digunakan untuk mencari harga yang aktif
        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }
}

