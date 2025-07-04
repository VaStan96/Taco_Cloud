package taco_proj.taco_cloud.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco_proj.taco_cloud.Utils;
import taco_proj.taco_cloud.data.OrderRepository;
import taco_proj.taco_cloud.entity.DTO_EmailOrder;
import taco_proj.taco_cloud.entity.TacoOrder;
import taco_proj.taco_cloud.entity.User;
import taco_proj.taco_cloud.messaging.OrderMessagingService;
import taco_proj.taco_cloud.propsHolders.OrderProps;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProps orderProps;
    private final OrderMessagingService messageService;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                            OrderProps orderProps, OrderMessagingService messageService){
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
        this.messageService = messageService;
    }
    
    @GetMapping("/current")
    public String orderForm(){
        // return View from resources/templates
        return "orderForm";
    }

    @GetMapping
    // Get orders for authentication user
    public String orderForUser(@AuthenticationPrincipal User user, Model model) {
        // get 1. page with "pageSize" objects
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
    

    @PostMapping()
    // checking validate for object TacoOrder from View and adding errors in object Errors
    // session for finish order
    public String processOrder(
        @Valid TacoOrder order, 
        Errors errors, 
        SessionStatus sessionStatus,
        @AuthenticationPrincipal User user){
        
        if (errors.hasErrors()){
            // return View from resources/templates
            return "orderForm";
        }
        
        order.setUser(user);
        order.setPlacedAt(new Date());

        order = orderRepository.save(order);
        
        // JMS
        messageService.sendOrder(order);

        // exit session
        sessionStatus.setComplete();
        // to Home
        return "redirect:/";
    }

    @Value("${taco.from_mail_key}")
    private String fromEmailKey;

    @PostMapping("/fromEmail")
    @ResponseBody
    public ResponseEntity<Void> handleEmailOrder(@RequestBody DTO_EmailOrder order, @RequestHeader("X-API-KEY") String apiKey){
        if (!fromEmailKey.equals(apiKey)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        System.out.println(order);
        // convert
        TacoOrder newOrder = Utils.emailOrderToTacoOrder(order);
        // DB
        newOrder = orderRepository.save(newOrder);
        // JMS
        messageService.sendOrder(newOrder);

        return ResponseEntity.ok().build();
    }
}
