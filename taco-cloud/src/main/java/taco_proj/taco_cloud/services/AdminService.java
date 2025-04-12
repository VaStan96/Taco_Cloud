package taco_proj.taco_cloud.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import taco_proj.taco_cloud.data.OrderRepository;

@Service
public class AdminService {

    private OrderRepository orderRepository;

    public AdminService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    // The annotation for check Role before method making
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    // SecurityPack have also Post-checking

    // @PostAuthorize("hasRole(‘ADMIN’) || " +
    // "returnObject.user.username == authentication.name")
    // public TacoOrder getOrder(long id) {
    // ...
    // }
}
