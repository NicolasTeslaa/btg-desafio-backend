package com.tesla.btg_pactual_desafio_backend.service;

import com.tesla.btg_pactual_desafio_backend.dto.OrderRecord;
import com.tesla.btg_pactual_desafio_backend.entity.OrderEntity;
import com.tesla.btg_pactual_desafio_backend.entity.OrderItemEntity;
import com.tesla.btg_pactual_desafio_backend.exception.OrderIsExistingException;
import com.tesla.btg_pactual_desafio_backend.mapping.OrderMapper;
import com.tesla.btg_pactual_desafio_backend.repository.OrderRepository;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository _orderRepository;
    private final OrderMapper _orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        _orderRepository = orderRepository;
        _orderMapper = new OrderMapper();
    }

    public OrderEntity CreateOrder(OrderRecord order) {
        ValidateOrder(order);

        OrderEntity entity = _orderMapper.toEntity(order);

        return _orderRepository.save(entity);
    }

    private void ValidateOrder(OrderRecord order) {
        var exists = _orderRepository.existsById(order.codigoPedido());

        if (exists) {
            throw new AmqpRejectAndDontRequeueException("order already exists");
        }

        var totalItens = order.itens().stream()
                .map(item -> item.preco()
                        .multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalItens.compareTo(BigDecimal.ZERO) <= 0){
            throw new  OrderIsExistingException("order already exists");
        }
    }

    public Page<OrderRecord> findAllOrders(Pageable pageable) {
        return _orderRepository
                .findAll(pageable)
                .map(_orderMapper::toRecord);
    }

    public Page<OrderRecord> findByClient(Long clientId, Pageable pageable) {
        return _orderRepository.findByClientId(clientId, pageable).map(_orderMapper::toRecord);
    }

    public BigDecimal valueTotalForOrder(Long orderId) {
        return _orderRepository.findByOrderId(orderId).getTotal();
    }

    public BigDecimal quantityOrdersForClient(Long clientId) {
        long total = _orderRepository.countByClientId(clientId);
        return BigDecimal.valueOf(total);
    }
}
