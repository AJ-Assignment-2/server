package clients.reception;

import model.Order.Order;
import model.Order.OrderComparator;
import model.Order.OrderState;
import server.rmi.reception.ReceptionService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This model maintains the state of the reception client application.
 * It polls the RMI server for order information and notifies its observers when order data is updated.
 */
public class ReceptionModel implements ObservableReceptionModel {

    private static final Logger LOGGER = Logger.getLogger(ReceptionModel.class.getName());

    private List<ReceptionModelObserver> observers;

    private ReceptionService receptionService;
    private List<Order> servedOrders;
    private List<Order> billedOrders;

    /**
     * Initialise member variables and start a new thread that polls the RMI service at a set interval.
     */
    public ReceptionModel() {
        observers = new ArrayList<>();
        servedOrders = new ArrayList<>();
        billedOrders = new ArrayList<>();
        try {
            receptionService = (ReceptionService) Naming.lookup("rmi://127.0.0.1/Reception");

            new Thread(() -> {
                while (true) {
                    try {
                        setOrders(receptionService.getOrders());
                        Thread.sleep(1000);
                    } catch (RemoteException e) {
                        LOGGER.log(Level.WARNING, "Attempt to retrieve orders through RMI failed", e);
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARNING, "Error sleeping thread", e);
                    }
                }
            }).start();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to reception RMI service", e);
        }
    }

    /**
     * Notify observers that the model's orders have been updated.
     */
    private void notifyOrdersUpdated() {
        for (ReceptionModelObserver observer : observers) {
            observer.ordersReceivedFromServer(this.servedOrders, this.billedOrders);
        }
    }

    /**
     * Set the model list of orders, splitting billed and served orders into separate lists and discarding the rest.
     * @param orders new list of orders
     */
    private void setOrders(List<Order> orders) {
        Collections.sort(orders, new OrderComparator());

        this.billedOrders = orders.stream().filter(order -> order.getState() == OrderState.BILLED)
                .collect(Collectors.toList());

        this.servedOrders = orders.stream().filter(order -> order.getState() == OrderState.SERVED)
                .collect(Collectors.toList());

        notifyOrdersUpdated();
    }

    /**
     * Tell the RMI server to change a provided orders state to billed.
     * @param order order to update
     */
    public void updateSelectedOrder(Order order) {
        try {
            receptionService.billOrder(order.getId());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Failed to send update command to RMI Server!", e);
        }
    }

    /**
     * Register a reception model observer.
     * @param observer the observer to register
     */
    @Override
    public void addReceptionModelObserver(ReceptionModelObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Remove a reception model observer.
     * @param observer The observer to unregister.
     */
    @Override
    public void removeReceptionModelObserver(ReceptionModelObserver observer) {
        this.observers.remove(observer);
    }
}
