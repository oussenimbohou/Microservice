package com.barack.inventoryservice.service;

import com.barack.inventoryservice.entity.Inventory;
import com.barack.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        return repository.findBySkuCode().isPresent();
    }
}
