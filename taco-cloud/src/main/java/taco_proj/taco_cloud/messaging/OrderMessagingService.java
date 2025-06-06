package taco_proj.taco_cloud.messaging;

import taco_proj.taco_cloud.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
