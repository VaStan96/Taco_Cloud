package taco_proj.taco_cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco_proj.taco_cloud.TacoOrder;
import taco_proj.taco_cloud.data.OrderRepository;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    
    @GetMapping("/current")
    public String orderForm(){
        // return View from resources/templates
        return "orderForm";
    }

    @PostMapping()
    // checking validate for object TacoOrder from View and adding errors in object Errors
    // session for finish order
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus){
        
        if (errors.hasErrors()){
            // return View from resources/templates
            return "orderForm";
        }
        
        orderRepository.save(order);
        // exit session
        sessionStatus.setComplete();
        // to Home
        return "redirect:/";
    }
}
