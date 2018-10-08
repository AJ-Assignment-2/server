package server.rmi.reception;

import model.Order.Order;
import model.Order.OrderState;
import server.service.OrderService.OrderAccessor;
import server.service.OrderService.OrderDao;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReceptionServiceImpl extends UnicastRemoteObject implements ReceptionService {
    private static final Logger LOGGER = Logger.getLogger(ReceptionServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;

    public ReceptionServiceImpl(Connection databaseConnection) throws RemoteException {
        orderAccessor = new OrderAccessor(databaseConnection);
    }

    @Override
    public void billOrder(Order order) {
        // TODO: Write a method in the OrderAccessor class that changes a given orders status and call it here.
    }

    @Override
    public List<Order> getPreparedOrders() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving prepared orders");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        return orderAccessor.getAllOrders().stream().filter(order -> order.getState() == OrderState.SERVED)
                .collect(Collectors.toList());
    }
}
