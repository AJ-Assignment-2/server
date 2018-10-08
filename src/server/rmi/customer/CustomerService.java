package server.rmi.customer;

import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderState;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerService extends Remote {
    /**
     * Creates an order in the remote database.
     * @param order The order to create in the database (the ID property should not be set).
     * @throws RemoteException
     */
    void createOrder(Order order) throws RemoteException;

    //OrderState getOrderStatus(Order order);

    /**
     * Get all menu items from the remote database.
     * @return A list containing all menu items in the database.
     * @throws RemoteException
     */
    List<MenuItem> getMenuItems() throws RemoteException;
}
