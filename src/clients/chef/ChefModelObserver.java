package clients.chef;

import model.Order.Order;

import java.util.List;

public interface ChefModelObserver {
    void waitingOrdersUpdated(List<Order> waitingOrders);
}
