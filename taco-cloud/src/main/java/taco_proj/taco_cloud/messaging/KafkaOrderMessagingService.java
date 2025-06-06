package taco_proj.taco_cloud.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import taco_proj.taco_cloud.TacoOrder;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService{

    private final KafkaTemplate <String, TacoOrder> kafka;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate <String, TacoOrder> kafka){
        this.kafka = kafka;
    }
    
    @Override
    public void sendOrder(TacoOrder order){
        kafka.send("tacocloud.orders.topic", order);
    }
}
