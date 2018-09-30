package service.OrderService;

import model.Order.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    void createOrder(Order order);
}
