package kitchen.demo.Service;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import kitchen.demo.Data.TacoOrder;

@ExtendWith(MockitoExtension.class)
public class KitchenServiceTest {
    
    // ----------------with Mocking------------------
    // @Mock
    // Deque<TacoOrder> orders = new ArrayDeque<>();
    
    // @InjectMocks
    // KitchenService service;

    // -------------witout Mocking-------------------
    private KitchenService service;

    @BeforeEach
    public void setUp(){
        service = new KitchenService();
    }

    @Test
    public void serviceTest(){
        TacoOrder order1 = new TacoOrder(1L, 
                        new Date(2000, 1, 1), 
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
        TacoOrder order2 = new TacoOrder(2L, 
                        new Date(2000, 1, 1), 
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

        TacoOrder order3 = new TacoOrder(3L, 
                        new Date(2000, 1, 1), 
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

        service.addOrder(order1);
        service.addOrder(order2);
        service.addOrder(order3);
        
        //-----execution will terminate after first failed test------- 
        // assertEquals(order1, service.takeNewOrder());
        // assertEquals(order2, service.takeNewOrder());
        // assertEquals(order3, service.takeNewOrder());

        //-----all tests will be performed------- 
        assertAll(
            () -> assertEquals(order1, service.takeNewOrder()),
            () -> assertEquals(order2, service.takeNewOrder()),
            () -> assertEquals(order3, service.takeNewOrder())
        );
    }

    @Test
    public void ServiceNullTest(){
        assertNull(service.takeNewOrder());
    }
}
