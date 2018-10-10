package server.service.OrderService;

import model.Order.Order;
import model.Order.OrderState;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    void createOrder(Order order);
    void setOrderState(OrderState state, int orderID);
}
