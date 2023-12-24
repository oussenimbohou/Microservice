package com.barack.inventoryservice.controller;

import com.barack.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService service;
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode){
        return service.isInStock(skuCode);
    }

}
