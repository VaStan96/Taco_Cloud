package kitchen.demo.Controllers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import kitchen.demo.Data.TacoOrder;
import kitchen.demo.Service.KitchenService;

@ExtendWith(MockitoExtension.class)
public class KitchenControllerTest {

    public Model model = new ExtendedModelMap();
    public TacoOrder order = new TacoOrder(1L, 
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
    
    @Mock
    public KitchenService service;

    @InjectMocks
    public KitchenController controller;

    @Test
    public void getShowOrderTest(){
        when(service.takeNewOrder()).thenReturn(order);

        assertEquals("kitchen", controller.showOrder(model));
        assertEquals(order, model.getAttribute("order"));
    }

    @Test
    public void postTakeNewTest(){
        when(service.takeNewOrder()).thenReturn(order);

        assertEquals("kitchen", controller.takeNew(model));
        assertEquals(order, model.getAttribute("order"));
    }

    @Test
    public void postSendAltTest(){
        when(service.takeNewOrder()).thenReturn(order);

        String view = controller.sendAlt(1L, model);

        verify(service).altOrder(1L);
        assertEquals("kitchen", view);
        assertEquals(order, model.getAttribute("order"));
    }
}
