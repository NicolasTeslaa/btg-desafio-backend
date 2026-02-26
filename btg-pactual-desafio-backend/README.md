# btg-pactual-desafio-backend

API de pedidos com consumo de eventos via RabbitMQ e persistência em MongoDB. O serviço expõe endpoints REST para consulta e processa mensagens de criação de pedidos.

**Stack**
- Java 25
- Spring Boot 4.0.3 (Web, AMQP, Data MongoDB)
- MongoDB
- RabbitMQ
- Maven

**Como rodar**
1. Com Docker (recomendado)
```bash
docker compose up --build
```

2. Local (sem Docker)
```bash
./mvnw spring-boot:run
```

**Configuração**
Valores padrão em `src/main/resources/application.properties`:
- `server.port=3000`
- `spring.mongodb.uri=mongodb://admin:123@localhost:27017/btgpactualdb?authSource=admin`
- `spring.rabbitmq.host=localhost`
- `spring.rabbitmq.port=5672`
- `spring.rabbitmq.username=admin`
- `spring.rabbitmq.password=123`

Com Docker Compose, as variáveis são sobrescritas em `docker-compose.yml`.

**Fila RabbitMQ**
- Nome: `btg-pactual-order-created-queue`
- Consumidor: `OrderCreatedListener`
- Conversor JSON: Jackson

Payload esperado (exemplo):
```json
{
  "codigoPedido": 1001,
  "codigoCliente": 2001,
  "itens": [
    { "produto": "Notebook", "quantidade": 2, "preco": 3500.00 },
    { "produto": "Mouse", "quantidade": 1, "preco": 150.00 }
  ]
}
```

**Endpoints**
- `GET /order`
  - Lista pedidos paginados.
  - Query params: `page`, `size`, `sort`
- `GET /order/clients/{clientId}`
  - Lista pedidos de um cliente (paginado).
- `GET /order/totalValueForOrder/{orderId}`
  - Retorna o total do pedido.
- `GET /order/QuantityOrdersForClient/{clientId}`
  - Retorna a quantidade de pedidos do cliente.

**Persistência**
- Coleção principal: `tbl_orders`
- Campos: `orderId`, `clientId`, `total`, `itens`
- O total do pedido é calculado a partir dos itens no mapeamento (`OrderMapper`).

**Observações de inicialização**
Na subida da aplicação, um `CommandLineRunner` executa um write curto no MongoDB para garantir a criação do banco e remove o documento logo em seguida.
