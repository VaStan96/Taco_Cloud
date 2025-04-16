package taco_proj.taco_cloud.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import taco_proj.taco_cloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{

    @Query("SELECT t FROM Taco t")
    Page<Taco> findAll(Pageable page);
    
}
