package taco_proj.taco_cloud.messaging;

import taco_proj.taco_cloud.entity.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
