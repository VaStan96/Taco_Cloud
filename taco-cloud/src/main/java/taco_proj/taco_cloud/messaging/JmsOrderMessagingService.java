package taco_proj.taco_cloud.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import taco_proj.taco_cloud.TacoOrder;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{
    
    private final JmsTemplate jms;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms){
        this.jms = jms;
    }


    // Variant for jms.send mit castome realize createMessage()
    // // --------------------
    // // @Override
    // // public void sendOrder(TacoOrder order){
    // //     jms.send(new MessageCreator() {
    // //             @Override
    // //             public Message createMessage(Session session) throws JMSException{
    // //                 return session.createObjectMessage(order);
    // //             }
    // //     });
    // // }
    
    // @Override
    // public void sendOrder(TacoOrder order){
    //     jms.send("tacocloud.order.queue",
    //              session -> session.createObjectMessage(order));
    // }

    // Variant for jms.convertAndSend mit auto convert in Object
    @Override
    public void sendOrder(TacoOrder order){
        jms.convertAndSend("tacocloud.order.queue", 
                            order,
                            // Add header in Message as link to method
                            this::addOrderSource
                        );
    }

    private Message addOrderSource(Message message) throws JMSException{
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        message.setStringProperty("_type", "order");

        if (message instanceof TextMessage) {
        String json = ((TextMessage) message).getText();
        System.out.println("Отправляемое сообщение (JSON):");
        System.out.println(json);
        } else {
            System.out.println("Не TextMessage, а: " + message.getClass().getSimpleName());
        }

        return message;
    }
}
