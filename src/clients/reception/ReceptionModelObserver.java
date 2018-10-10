package clients.reception;

import model.Order.Order;

import java.util.List;

public interface ReceptionModelObserver {
    void ordersReceivedFromServer(List<Order> servedOrders, List<Order> billedOrders);
}
