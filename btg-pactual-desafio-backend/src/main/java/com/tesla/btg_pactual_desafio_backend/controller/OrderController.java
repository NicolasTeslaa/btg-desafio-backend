package com.tesla.btg_pactual_desafio_backend.controller;

import com.tesla.btg_pactual_desafio_backend.dto.OrderRecord;
import com.tesla.btg_pactual_desafio_backend.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService _orderService;

    public OrderController(OrderService orderService) {
        _orderService = orderService;
    }

    @GetMapping
    public Page<OrderRecord> getOrders(@PageableDefault(page = 0, size = 20) Pageable pageable)
    {
        return _orderService.findAllOrders(pageable);
    }

    @GetMapping("/clients/{clientId}")
    public Page<OrderRecord> getOrdersForClients(@PathVariable Long clientId, @PageableDefault(page = 0, size = 20) Pageable pageable)
    {
        return _orderService.findByClient(clientId, pageable);
    }

    @GetMapping("/totalValueForOrder/{orderId}")
    public BigDecimal getTotalValueForOrder(@PathVariable Long orderId)
    {
        return _orderService.valueTotalForOrder(orderId);
    }

    @GetMapping("QuantityOrdersForClient/{clientId}")
    public BigDecimal getQuantityItemsForClient(@PathVariable Long clientId)
    {
        return _orderService.quantityOrdersForClient(clientId);
    }
}