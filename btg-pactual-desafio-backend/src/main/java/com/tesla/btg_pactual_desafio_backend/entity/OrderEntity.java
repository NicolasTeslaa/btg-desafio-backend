package com.tesla.btg_pactual_desafio_backend.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "tbl_orders")
public class OrderEntity {
    @MongoId
    private Long orderId;

    @Indexed(name = "idx_codigo_cliente")
    private Long clientId;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;

    private List<OrderItemEntity> itens;
}
