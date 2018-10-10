package server.rmi.chef;

import model.Order.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChefService extends Remote {
    /**
     * Retrieve all orders in the database that have the WAITING state.
     * @return All orders in the database having the WAITING state.
     * @throws RemoteException
     */
    List<Order> getAllOrders() throws RemoteException;

    /**
     * Change the given orders status to SERVED
     * @param order The order to mark as SERVED
     * @throws RemoteException
     */
    void markOrderServed(Order order) throws RemoteException;
}
