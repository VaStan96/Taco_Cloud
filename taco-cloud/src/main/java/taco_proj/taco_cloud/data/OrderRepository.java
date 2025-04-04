package taco_proj.taco_cloud.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import taco_proj.taco_cloud.TacoOrder;


public interface OrderRepository extends CrudRepository<TacoOrder, Long>{
    // Varianten for modification repository methods
    List<TacoOrder> findByDeliveryZip(String deliveryZip);
    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);    
    List<TacoOrder> getByDeliveryCityOrderByDeliveryName(String city);
}
