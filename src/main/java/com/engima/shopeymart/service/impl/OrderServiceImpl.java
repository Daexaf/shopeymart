package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.dto.request.OrderRequest;
import com.engima.shopeymart.dto.response.*;
import com.engima.shopeymart.entity.*;
import com.engima.shopeymart.repository.OrderRepository;
import com.engima.shopeymart.service.CustomerService;
import com.engima.shopeymart.service.OrderService;
import com.engima.shopeymart.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductPriceService productPriceService;

    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        //todo:1. validasi customernya dulu
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());

        //todo:2. convert orderDetailRequest to orderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(orderDetailRequest ->{
           //todo:3. validasi product price
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            return OrderDetail.builder()
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();
        //todo:4. Create New Order
        Order order = Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .name(customerResponse.getFullName())
                        .address(customerResponse.getAddress())
                        .mobilePhone(customerResponse.getPhone())
                        .build())
                .transDate(LocalDateTime.now())
                .orderDetails(orderDetails)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
           //todo:5 set order dari orderDetail setelah membuat order baru
            orderDetail.setOrder(order);
            System.out.println(orderDetail);
            //todo:6 ubah stok dari jumlah pembelian
            ProductPrice currentProductPrice = orderDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - orderDetail.getQuantity());
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .quantity(orderDetail.getQuantity())
                    //todo: 7. convert product ke productResponse(productPrice)
                    .product(ProductResponse.builder()
                            .id(currentProductPrice.getProduct().getId())
                            .productName(currentProductPrice.getProduct().getName())
                            .desc(currentProductPrice.getProduct().getDescription())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())
                            //todo: 8. convert store ke storeResponse(productPrice)
                            .store(StoreResponse.builder()
                                    .id(currentProductPrice.getStore().getId())
                                    .noSiup(currentProductPrice.getStore().getNoSiup())
                                    .storeName(currentProductPrice.getStore().getName())
                                    .build())
                            .build())
                    .build();
        }).toList();

        //todo: 9. convert customer ke customerResponse
//        CustomerResponse customerResponses = customerResponse.toBuilder()
//                .id(order.getCustomer().getId())
//                .fullName(order.getCustomer().getName())
//                .phone(order.getCustomer().getMobilePhone())
//                .address(order.getCustomer().getAddress())
//                .build();

        //todo: 10. return orderResponse
        return OrderResponse.builder()
                .orderId(order.getId())
                .customer(customerResponse)
                .orderDetails(orderDetailResponses)
                .transDate(order.getTransDate())
                .build();
    }

    @Override
    public OrderResponse getOrderId(String id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }
}
