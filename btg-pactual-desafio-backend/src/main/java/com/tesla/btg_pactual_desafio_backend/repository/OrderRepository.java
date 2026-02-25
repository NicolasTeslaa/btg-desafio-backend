package com.tesla.btg_pactual_desafio_backend.repository;

import com.tesla.btg_pactual_desafio_backend.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    public Page<OrderEntity> findByClientId(Long clientId, Pageable pageable);
    public OrderEntity findByOrderId(Long orderId);
    long countByClientId(Long clientId);
}
