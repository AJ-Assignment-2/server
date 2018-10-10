package server.rmi.reception;

import model.Order.Order;
import model.Order.OrderState;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReceptionService extends Remote {
    /**
     * Change a given orders state to BILLED
     * @param orderId The id of the order to update
     * @throws RemoteException
     */
    void billOrder(int orderId) throws RemoteException;

    /**
     * Returns all orders in the database
     * @return All orders in the database
     * @throws RemoteException
     */
    List<Order> getOrders() throws RemoteException;
}
