package com.tesla.btg_pactual_desafio_backend.listener;

import com.tesla.btg_pactual_desafio_backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.tesla.btg_pactual_desafio_backend.dto.OrderRecord;

import static com.tesla.btg_pactual_desafio_backend.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {
    private final OrderService _orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    public OrderCreatedListener(OrderService orderService) {
        _orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderRecord> message) {
        message.getPayload().itens().forEach(item -> {
            System.out.printf("Received order %s for customer %s: %d x %s at $%.2f each%n",
                message.getPayload().codigoPedido(),
                message.getPayload().codigoCliente(),
                item.quantidade(),
                item.produto(),
                item.preco());
        });

        logger.info("Message consumed: {}", message.getPayload());

        _orderService.CreateOrder(message.getPayload());
    }
}
