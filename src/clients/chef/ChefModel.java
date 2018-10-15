package clients.chef;

import model.Order.Order;
import model.Order.OrderComparator;
import model.Order.OrderState;
import server.rmi.chef.ChefService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This model maintains the state of the chef application
 * It queries the server at a set interval to get the most recent order data.
 * This model notifies its observers of state change when it receives data from the server.
 */
public class ChefModel implements ObservableChefModel {
    private static final Logger LOGGER = Logger.getLogger(ChefModel.class.getName());

    private List<Order> waitingOrders;
    private List<Order> servedOrders;
    private ChefService chefService;
    private List<ChefModelObserver> observers;

    /**
     * Connect to the RMI service and poll it for information at a set interval on a background thread.
     */
    public ChefModel(){
        observers = new ArrayList<>();
        servedOrders = new ArrayList<>();
        waitingOrders = new ArrayList<>();

        try {
            chefService = (ChefService) Naming.lookup("rmi://127.0.0.1/Chef");

            new Thread(() -> {
                try {
                    while (true) {
                        // Retrieve all orders from the RMI server.
                        setOrders(chefService.getAllOrders());
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Attempt to retrieve waiting orders failed.", e);
                }
            }).start();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to chef RMI service", e);
        }
    }

    // Set the model's orders, separating orders with the waiting or served state.
    public void setOrders(List<Order> orders) {
        Collections.sort(orders, new OrderComparator());
        this.waitingOrders = orders.stream().filter(order -> order.getState() == OrderState.WAITING)
                .collect(Collectors.toList());

        this.servedOrders = orders.stream().filter(order -> order.getState() == OrderState.SERVED)
                .collect(Collectors.toList());
        notifyOrdersChanged();

    }

    /**
     * Notify observers that the model's orders have changed.
     */
    private void notifyOrdersChanged() {
        for (ChefModelObserver observer : observers) {
            observer.ordersUpdated(waitingOrders, servedOrders);
        }
    }

    /**
     * Update a selected order's state on the RMI server
     *
     * @param order The order to update.
     */
    public void updateSelectedOrder(Order order) {
        try {
            // Get the ID of the order and tell the RMI server to change its state to SERVED.
            chefService.markOrderServed(order.getId());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Failed to send update command to RMI Server!", e);
        }
    }

    /**
     * Register an observer to the model.
     * @param observer the observer to add
     */
    @Override
    public void addChefModelObserver(ChefModelObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove a registered observer to the model.
     * @param observer model observer to remove.
     */
    @Override
    public void removeChefModelObserver(ChefModelObserver observer) {
        observers.remove(observer);
    }
}
