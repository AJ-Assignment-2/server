package server.service.OrderService;

import model.Order.Order;
import model.Order.OrderState;

import java.util.List;

/**
 * The contract specifying what operations may be performed on Orders in the Derby database.
 */
public interface OrderDao {
    /**
     * Retrieve all orders in the database.
     * @return all orders in the Derby database.
     */
    List<Order> getAllOrders();

    /**
     * Create an order in the derby database.
     * @param order the order to create in the database (id should not be supplied)
     */
    void createOrder(Order order);

    /**
     * Update the state of an order in the database.
     * @param state the state to set on the order
     * @param orderID the order's ID
     */
    void setOrderState(OrderState state, int orderID);
}
