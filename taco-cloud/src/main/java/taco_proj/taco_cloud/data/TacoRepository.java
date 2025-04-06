package taco_proj.taco_cloud.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import taco_proj.taco_cloud.Taco;

public interface  TacoRepository extends CrudRepository<Taco, UUID>{
    
}
