package server.rmi.customer;

import model.MenuItem.MenuItem;
import model.Order.Order;
import server.service.MenuItemService.MenuItemAccessor;
import server.service.MenuItemService.MenuItemDao;
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
public class CustomerServiceImpl extends UnicastRemoteObject implements CustomerService {
    private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;
    private MenuItemDao menuItemAccessor;

    public CustomerServiceImpl(Connection connection) throws RemoteException {
        orderAccessor = new OrderAccessor(connection);
        menuItemAccessor = new MenuItemAccessor(connection);
    }

    /**
     * Creates an order in the derby database.
     * @param order The order to create in the database (the ID property should not be set).
     */
    @Override
    public void createOrder(Order order) throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Creating a new order");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        // Create an order in the Derby database.
        orderAccessor.createOrder(order);
    }

    /**
     * Get all menu items in the derby database.
     * @return all menu items in the Derby database.
     */
    @Override
    public List<MenuItem> getMenuItems() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving all menu items");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }

        // Return all menu items in the derby database.
        return menuItemAccessor.readMenuItems();
    }
}
