package kitchen.demo.Messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import kitchen.demo.TacoOrder;

@Component
public class JmsOrderReceiver implements OrderReceiver{
    private final JmsTemplate jms;
    

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter){
        this.jms = jms;
    }

    @Override
    public TacoOrder receiveOrder(){
        TacoOrder order = (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
        return order;
    }
}
