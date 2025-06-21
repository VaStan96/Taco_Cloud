package taco_proj.taco_cloud.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import taco_proj.taco_cloud.data.OrderRepository;
import taco_proj.taco_cloud.entity.TacoOrder;
import taco_proj.taco_cloud.messaging.OrderMessagingService;


@RestController
@RequestMapping(path="/api/orders", produces="application/json")
@CrossOrigin("localhost:8080")
public class RestOrderController {

    private final OrderRepository orderRepository;
    private final OrderMessagingService messageService;

    public RestOrderController (OrderRepository orderRepository, OrderMessagingService messageService){
        this.orderRepository = orderRepository;
        this.messageService = messageService;
    }
    
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        messageService.sendOrder(order);
        return orderRepository.save(order);
    }
    

    // change the entire object, despite empty fields
    @PutMapping(path="/{orderId}", consumes="application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId, 
                                @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    // change only those fields that were passed
    @PatchMapping(path="/{orderId}", consumes="application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, 
                                @RequestBody TacoOrder patch){
        
        TacoOrder order = orderRepository.findById(orderId).get();
        
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId){
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }
}
