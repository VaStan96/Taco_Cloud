package kitchen.demo;

import org.springframework.stereotype.Service;

import kitchen.demo.Messaging.RabbitOrderReceiver;
import kitchen.demo.Messaging.OrderReceiver;

@Service
public class KitchenService {

    private final OrderReceiver jms;

    public KitchenService(RabbitOrderReceiver jms){
        this.jms = jms;
    }

    public TacoOrder takeNewOrder(){
        return jms.receiveOrder();
    }

    public void altOrder(Long id){

    }
}
