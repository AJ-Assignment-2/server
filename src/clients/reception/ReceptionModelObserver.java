package clients.reception;

import model.Order.Order;

import java.util.List;

public interface ReceptionModelObserver {
    /**
     * Be notified that new orders have been received from the RMI server.
     * @param servedOrders new served orders.
     * @param billedOrders new billed orders.
     */
    void ordersReceivedFromServer(List<Order> servedOrders, List<Order> billedOrders);
}
