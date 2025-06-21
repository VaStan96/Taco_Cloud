package kitchen.demo;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchen.demo.Data.TacoOrder;
import kitchen.demo.Messaging.KafkaOrderListener;
import kitchen.demo.Service.KitchenService;

@ExtendWith(MockitoExtension.class)
public class KafkaOrderListenerTest {
    
    @Mock
    public KitchenService service;
    
    @InjectMocks
    public KafkaOrderListener listener;

    @Test
    public void hadleTest(){
        TacoOrder order = new TacoOrder(1L, 
                        new Date(2000 - 1900, 0, 1), 
                        "test1", 
                        "null", 
                        "null", 
                        "null", 
                        "null", 
                        "null", 
                        "null", 
                        "null", 
                        null, 
                        null);

        listener.handle(order);
        // test, that in Service method addOrder(order) calling
        verify(service).addOrder(order);
    }
}

