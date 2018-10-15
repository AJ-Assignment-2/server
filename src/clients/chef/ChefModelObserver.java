package clients.chef;

import model.Order.Order;

import java.util.List;

/**
 * Contract for chef model observer's.
 */
public interface ChefModelObserver {
    // Be notified that the orders have been updated
    void ordersUpdated(List<Order> waitingOrders, List<Order> servedOrders);
}
