package com.barack.inventoryservice.controller;

import com.barack.inventoryservice.dto.InventoryResponse;
import com.barack.inventoryservice.entity.Inventory;
import com.barack.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService service;
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventories(){
        return service.getAllInventories();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes){
        return service.isInStock(skuCodes);
    }

}
