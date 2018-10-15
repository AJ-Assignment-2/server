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

/**
 * An implementation of the ChefService that accesses the server's embedded derby database
 * and manipulates/retrieves its data. Clients can call these methods.
 */
public class ChefServiceImpl extends UnicastRemoteObject implements ChefService {
    private static final Logger LOGGER = Logger.getLogger(ChefServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;

    /**
     * Initialise the order accessor used to manipulate the derby database.
     * @param connection The connection to connect to the database with.
     */
    public ChefServiceImpl(Connection connection) throws RemoteException {
        orderAccessor = new OrderAccessor(connection);
    }

    /**
     * Retrieve all orders from the derby database.
     * @return all orders in the database.
     */
    @Override
    public List<Order> getAllOrders() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving all orders with the WAITING state");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        // Return all orders in the Derby database.
        return orderAccessor.getAllOrders();
    }

    /**
     * Change the value of an orders state column to Served.
     * @param orderId The id of the order to mark as SERVED
     * @throws RemoteException
     */
    @Override
    public void markOrderServed(int orderId) throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Updating order state");

            // Update a given orders state in the derby database.
            orderAccessor.setOrderState(OrderState.SERVED, orderId);
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }
    }
}
