package kitchen.demo.Messaging;

import kitchen.demo.TacoOrder;

public interface OrderReceiver {
    TacoOrder receiveOrder();
}
