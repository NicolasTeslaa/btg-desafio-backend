package com.tesla.btg_pactual_desafio_backend.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String produto, Integer quantidade, BigDecimal preco) {}
