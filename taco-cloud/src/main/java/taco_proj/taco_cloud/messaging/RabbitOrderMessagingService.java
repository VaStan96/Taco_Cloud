package taco_proj.taco_cloud.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taco_proj.taco_cloud.TacoOrder;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService{

    private final RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit){
        this.rabbit = rabbit;
    }

    // // manual converter und serialization
    // @Override
    // public void sendOrder(TacoOrder order){
    //     MessageConverter converter = rabbit.getMessageConverter();
    //     MessageProperties props = new MessageProperties();
    //     Message message = converter.toMessage(order, props);
    //     rabbit.send("tacocloud.order", message);
    // }

    // auto converte
    @Override
    public void sendOrder(TacoOrder order){
        rabbit.convertAndSend("tacocloud.order",
                             "kitchens.central",
                              order,
                              this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws AmqpException{
        MessageProperties props = message.getMessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        return message;
    }
    
}
