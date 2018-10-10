package server.rmi.chef;

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

public class ChefServiceImpl extends UnicastRemoteObject implements ChefService {
    private static final Logger LOGGER = Logger.getLogger(ChefServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;

    public ChefServiceImpl(Connection connection) throws RemoteException {
        orderAccessor = new OrderAccessor(connection);
    }

    @Override
    public List<Order> getAllOrders() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving all orders with the WAITING state");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }
        return orderAccessor.getAllOrders();
    }

    @Override
    public void markOrderServed(Order order) throws RemoteException {
        // TODO: Modify the OrderAccessor object to have the ability to update an Orders status then call method here.
    }
}
