package kitchen.demo.Integrations;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kitchen.demo.Data.TacoOrder;
import kitchen.demo.Service.KitchenService;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "tacocloud.orders.topic")
public class KafkaOrderListenerIT {
    private TacoOrder order = new TacoOrder(1L, 
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

    @Autowired
    private KafkaTemplate<String, String> kafka;
    @Autowired
    private KitchenService service;

    @Test
    public void getMessageFromKafkaTest(){
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try{
            json = mapper.writeValueAsString(order);
        }
        catch (JsonProcessingException e){
            json = "";
        }

        kafka.send("tacocloud.orders.topic", json);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            TacoOrder actual = service.takeNewOrder();
            assertEquals(order, actual);
        });
    }

}
