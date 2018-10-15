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

/**
 * An implementation of the CustomerService that accesses the server's embedded derby database
 * and manipulates/retrieves its data. Clients can call these methods.
 */
public class ReceptionServiceImpl extends UnicastRemoteObject implements ReceptionService {
    private static final Logger LOGGER = Logger.getLogger(ReceptionServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;

    public ReceptionServiceImpl(Connection databaseConnection) throws RemoteException {
        orderAccessor = new OrderAccessor(databaseConnection);
    }

    /**
     * Change the value of the state column for a given order to BILLED.
     * @param orderId The id of the order to update
     */
    @Override
    public void billOrder(int orderId) {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Updating order state");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        // Manipulate the derby database.
        orderAccessor.setOrderState(OrderState.BILLED, orderId);
    }

    /**
     * Returns all orders in the Derby database.
     * @return all orders in the Derby database.
     */
    @Override
    public List<Order> getOrders() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving prepared orders");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        // Retrieve data from the derby database.
        return orderAccessor.getAllOrders();
    }
}
