package kitchen.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/kitchen")
public class KitchenController {
    private final KitchenService service;

    @Autowired
    public KitchenController(KitchenService service){
        this.service = service;
    }

    @GetMapping()
    public String showOrder(Model model) {
        return prepareOrderModel(model);
    }

    @PostMapping("/takeNew")
    public String takeNew(Model model) {
        return prepareOrderModel(model);
    }

    @PostMapping("/send")
    public String takeNew(@RequestParam Long id, Model model) {
        service.altOrder(id);
        return prepareOrderModel(model);
    }
    
    
    private String prepareOrderModel(Model model){
        model.addAttribute("order", service.takeNewOrder());
        return "kitchen";
    }
}
