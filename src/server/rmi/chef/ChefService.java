package server.rmi.chef;

import model.Order.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The contract defining the operations the chef RMI service can perform.
 */
public interface ChefService extends Remote {
    /**
     * Retrieve all orders in the database.
     * @return All orders in the database.
     * @throws RemoteException
     */
    List<Order> getAllOrders() throws RemoteException;

    /**
     * Change the given order's status to SERVED
     * @param orderId The id of the order to mark as SERVED
     * @throws RemoteException
     */
    void markOrderServed(int orderId) throws RemoteException;
}
