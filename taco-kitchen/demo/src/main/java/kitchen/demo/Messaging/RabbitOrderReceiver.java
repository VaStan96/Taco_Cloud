package kitchen.demo.Messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kitchen.demo.TacoOrder;

@Component
public class RabbitOrderReceiver implements OrderReceiver{
    private final RabbitTemplate rabbit;
    private final MessageConverter converter;
    

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit, MessageConverter converter){
        this.rabbit = rabbit;
        this.converter = converter;
    }

    @Override
    public TacoOrder receiveOrder(){
        Message message = rabbit.receive("kitchens.central");
        return message != null
            ? (TacoOrder) converter.fromMessage(message)
            :null;
    }
}

// ---------Passive model for auto pull-------------
// @Component
// public class OrderListener {
    
//     private KitchenUI ui;
    
//     @Autowired
//     public OrderListener(KitchenUI ui) {
//         s.ui = ui;
//     }
    
//     @RabbitListener(queues = "kitchens.central")
//     public void receiveOrder(TacoOrder order) {
//         ui.displayOrder(order);
//     }
// }
