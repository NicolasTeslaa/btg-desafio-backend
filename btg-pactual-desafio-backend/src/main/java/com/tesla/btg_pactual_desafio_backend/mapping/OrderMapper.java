package com.tesla.btg_pactual_desafio_backend.mapping;

import com.tesla.btg_pactual_desafio_backend.dto.OrderItemEvent;
import com.tesla.btg_pactual_desafio_backend.dto.OrderRecord;
import com.tesla.btg_pactual_desafio_backend.entity.OrderEntity;
import com.tesla.btg_pactual_desafio_backend.entity.OrderItemEntity;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {

    public OrderRecord toRecord(OrderEntity entity) {
        return new OrderRecord(
                entity.getOrderId(),
                entity.getClientId(),
                toItemEventList(entity.getItens())
        );
    }

    public OrderEntity toEntity(OrderRecord dto) {
        var entity = new OrderEntity();
        entity.setOrderId(dto.codigoPedido());
        entity.setClientId(dto.codigoCliente());

        var itens = toItemEntityList(dto.itens());
        entity.setItens(itens);

        // total derived from items (single source of truth)
        entity.setTotal(calcTotal(dto.itens()));

        return entity;
    }

    private List<OrderItemEvent> toItemEventList(List<OrderItemEntity> itens) {
        if (itens == null) return List.of();

        return itens.stream()
                .map(this::toItemEvent)
                .toList();
    }

    private OrderItemEvent toItemEvent(OrderItemEntity item) {
        return new OrderItemEvent(item.getProduct(), item.getQuantity(), item.getPrice());
    }

    private List<OrderItemEntity> toItemEntityList(List<OrderItemEvent> itens) {
        if (itens == null) return List.of();

        return itens.stream()
                .map(this::toItemEntity)
                .toList();
    }

    private OrderItemEntity toItemEntity(OrderItemEvent item) {
        var entity = new OrderItemEntity();
        entity.setProduct(item.produto());
        entity.setQuantity(item.quantidade());
        entity.setPrice(item.preco());
        return entity;
    }

    private BigDecimal calcTotal(List<OrderItemEvent> itens) {
        if (itens == null) return BigDecimal.ZERO;

        return itens.stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
