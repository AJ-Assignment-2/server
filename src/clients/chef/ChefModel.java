/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderComparator;
import model.Order.OrderState;
import server.rmi.chef.ChefService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Imanuel
 */
public class ChefModel implements ObservableChefModel {
    private static final Logger LOGGER = Logger.getLogger(ChefModel.class.getName());

    private List<Order> waitingOrders;
    private List<Order> servedOrders;
    private ChefService chefService;
    private List<ChefModelObserver> observers;

    public ChefModel(){
        observers = new ArrayList<>();
        servedOrders = new ArrayList<>();
        waitingOrders = new ArrayList<>();

        try {
            chefService = (ChefService) Naming.lookup("rmi://127.0.0.1/Chef");

            new Thread(() -> {
                try {
                    while (true) {
                        setOrders(chefService.getAllOrders());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Attempt to retrieve waiting orders failed.", e);
                }
            }).start();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to chef RMI service", e);
        }
    }

    public void setOrders(List<Order> orders) {
        Collections.sort(orders, new OrderComparator());
        this.waitingOrders = orders.stream().filter(order -> order.getState() == OrderState.WAITING)
                .collect(Collectors.toList());

        this.servedOrders = orders.stream().filter(order -> order.getState() == OrderState.SERVED)
                .collect(Collectors.toList());
        notifyOrdersChanged();

    }

    private void notifyOrdersChanged() {
        for (ChefModelObserver observer : observers) {
            observer.ordersUpdated(waitingOrders, servedOrders);
        }
    }

    public void updateSelectedOrder(Order order) {
        try {
            chefService.markOrderServed(order.getId());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Failed to send update command to RMI Server!", e);
        }
    }

    @Override
    public void addChefModelObserver(ChefModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeChefModelObserver(ChefModelObserver observer) {
        observers.remove(observer);
    }
}
