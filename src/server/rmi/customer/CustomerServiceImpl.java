package server.rmi.customer;

import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderState;
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
import java.util.stream.Collectors;

public class CustomerServiceImpl extends UnicastRemoteObject implements CustomerService {
    private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrderDao orderAccessor;
    private MenuItemDao menuItemAccessor;

    public CustomerServiceImpl(Connection connection) throws RemoteException {
        orderAccessor = new OrderAccessor(connection);
        menuItemAccessor = new MenuItemAccessor(connection);
    }

    @Override
    public void createOrder(Order order) throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Creating a new order");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }
        orderAccessor.createOrder(order);
    }

    @Override
    public List<MenuItem> getMenuItems() throws RemoteException {
        try {
            LOGGER.log(Level.INFO,
                    "(" + RemoteServer.getClientHost() + ") Incoming Request: Retrieving all menu items");
        } catch (ServerNotActiveException e) {
            LOGGER.log(Level.SEVERE, "RMI server has shut down!", e);
        }
        return menuItemAccessor.readMenuItems();
    }
}
