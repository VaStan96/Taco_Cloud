package taco_proj.taco_cloud;

import java.util.Date;
import java.util.List;

import taco_proj.taco_cloud.entity.DTO_EmailOrder;
import taco_proj.taco_cloud.entity.Taco;
import taco_proj.taco_cloud.entity.TacoOrder;
import taco_proj.taco_cloud.entity.User;

public class Utils {
    public static TacoOrder emailOrderToTacoOrder(DTO_EmailOrder order){
        TacoOrder newOrder = new TacoOrder();
        
        newOrder.setPlacedAt(new Date());
        newOrder.setDeliveryName(order.getUserName());
        List<Taco> tacos = order.getTacos();
        for(Taco taco : tacos){
            newOrder.addTaco(taco);
        }

        newOrder.setDeliveryStreet("EmailOrder");
        newOrder.setDeliveryCity ("EmailOrder");
        newOrder.setDeliveryState ("EmailOrder");
        newOrder.setDeliveryZip ("EmailOrder");

        newOrder.setCcNumber ("5062 8212 3456 7892");
        newOrder.setCcExpiration ("01/20");
        newOrder.setCcCVV ("000");

        newOrder.setUser (new User("EmailUser", "password", 
                                    order.getUserName(), "EmailOrder", 
                                    "EmailOrder", "EmailOrder", "EmailOrder", 
                                    "EmailOrder"));


        return newOrder;
    }
}
