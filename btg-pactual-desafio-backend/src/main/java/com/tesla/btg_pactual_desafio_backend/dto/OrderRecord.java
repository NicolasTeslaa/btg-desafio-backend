package com.tesla.btg_pactual_desafio_backend.dto;

import java.util.List;

public record OrderRecord(Long codigoPedido, Long codigoCliente, List<OrderItemEvent> itens)
{
}  
