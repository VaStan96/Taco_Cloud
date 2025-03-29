package taco_proj.taco_cloud.data;

import taco_proj.taco_cloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
