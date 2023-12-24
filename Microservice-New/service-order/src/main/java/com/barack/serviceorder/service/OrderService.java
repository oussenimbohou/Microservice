package com.barack.serviceorder.service;

import com.barack.serviceorder.dto.OrderLineItemsDto;
import com.barack.serviceorder.dto.OrderRequest;
import com.barack.serviceorder.entity.Order;
import com.barack.serviceorder.entity.OrderLineItems;
import com.barack.serviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    public Order placeOrder(OrderRequest request){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        return repository.save(order);
    }
    public List<Order> getAllOrders(){
        return repository.findAll();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
