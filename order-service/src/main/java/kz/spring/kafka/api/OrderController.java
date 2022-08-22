package kz.spring.kafka.api;

import kz.spring.kafka.dto.Order;
import kz.spring.kafka.dto.OrderEvent;
import kz.spring.kafka.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProducer producer;

    @PostMapping
    public String addOrders(@RequestBody Order order){
        UUID uuid = UUID.randomUUID();
        order.setOrderId(String.valueOf(uuid));
        OrderEvent event = new OrderEvent();
        event.setMessage("order status is in pending state");
        event.setStatus("PENDING");
        event.setOrder(order);
        producer.sendMessage(event);
        return "Success";
    }

}
