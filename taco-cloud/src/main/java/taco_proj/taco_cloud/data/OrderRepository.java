package taco_proj.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;

import taco_proj.taco_cloud.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{
    
}
