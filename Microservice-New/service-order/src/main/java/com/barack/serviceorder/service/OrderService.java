package com.barack.serviceorder.service;

import com.barack.serviceorder.dto.InventoryResponse;
import com.barack.serviceorder.dto.OrderLineItemsDto;
import com.barack.serviceorder.dto.OrderRequest;
import com.barack.serviceorder.entity.Order;
import com.barack.serviceorder.entity.OrderLineItems;
import com.barack.serviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final WebClient.Builder webClientBuilder;
    public String placeOrder(OrderRequest request){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponse = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventories/all")
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        log.info("All inventories infos: {}", inventoryResponse);
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventories",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponsesArray != null;
        boolean allProductInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if(allProductInStock){
            repository.save(order);
            return "Order Successfully placed...";
        }
        return "Product is not in stock...";
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
