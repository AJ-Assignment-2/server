package server.rmi.reception;

import model.Order.Order;
import model.Order.OrderState;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReceptionService extends Remote {
    /**
     * Change a given orders state to BILLED
     * @param order The order to set to the BILLED status
     * @throws RemoteException
     */
    void billOrder(Order order) throws RemoteException;

    /**
     * Returns all orders in the database with the SERVED state
     * @return All orders in the database that have the SERVED state
     * @throws RemoteException
     */
    List<Order> getPreparedOrders() throws RemoteException;
}
