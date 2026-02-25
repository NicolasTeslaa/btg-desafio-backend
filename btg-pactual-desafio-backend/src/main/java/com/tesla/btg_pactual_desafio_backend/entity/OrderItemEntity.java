package com.tesla.btg_pactual_desafio_backend.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "tbl_order_items")
public class OrderItemEntity {
    private String product;

    @Field(targetType = FieldType.INT32)
    private Integer quantity;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;
}
