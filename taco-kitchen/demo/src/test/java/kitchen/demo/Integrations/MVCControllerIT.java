package kitchen.demo.Integrations;

import java.util.Date;

import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import kitchen.demo.Data.TacoOrder;
import kitchen.demo.Service.KitchenService;

@SpringBootTest
@AutoConfigureMockMvc
public class MVCControllerIT {
    
    private Model model = new ExtendedModelMap();
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
    private MockMvc mockMvc;

    @Autowired
    private KitchenService service;

    @Test
    public void getTest() throws Exception{
        mockMvc.perform(get("/kitchen"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("kitchen"))
                        .andExpect(model().attribute("order", nullValue()));

        service.addOrder(order);

        mockMvc.perform(get("/kitchen"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("kitchen"))
                        .andExpect(model().attribute("order", order));
    }

    @Test
    public void postTakeNewTest() throws Exception{
        service.addOrder(order);

        mockMvc.perform(post("/kitchen/takeNew"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("kitchen"))
                        .andExpect(model().attribute("order", order));
    }

    @Test
    public void postSendAltTest() throws Exception{
        service.addOrder(order);

        mockMvc.perform(post("/kitchen/send").param("id", "1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("kitchen"));
    }

}
