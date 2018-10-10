package clients.chef;

import model.Order.Order;

import java.util.List;

public interface ChefModelObserver {
    void ordersUpdated(List<Order> waitingOrders, List<Order> servedOrders);
}
