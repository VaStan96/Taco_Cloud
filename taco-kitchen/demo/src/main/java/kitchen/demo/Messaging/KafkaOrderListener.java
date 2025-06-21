package kitchen.demo.Messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import kitchen.demo.Data.TacoOrder;
import kitchen.demo.Service.KitchenService;

@Component
public class KafkaOrderListener {

    private final KitchenService service;

    @Autowired
    public KafkaOrderListener(KitchenService service){
        this.service = service;
    }
    
    @KafkaListener(topics="tacocloud.orders.topic")
    public void handle(TacoOrder order){
        service.addOrder(order);
    }
}
