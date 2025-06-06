package kitchen.demo;

import java.util.ArrayDeque;
import java.util.Deque;

import org.springframework.stereotype.Service;


@Service
public class KitchenService {

    private final Deque<TacoOrder> orders = new ArrayDeque<>();

    public TacoOrder takeNewOrder(){
        return orders.pollFirst();
    }

    public void altOrder(Long id){

    }

    public void addOrder(TacoOrder order){
        orders.addLast(order);
    }
}
