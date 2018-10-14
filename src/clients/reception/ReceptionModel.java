/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import model.Order.Order;
import model.Order.OrderState;
import server.rmi.reception.ReceptionService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Imanuel
 */
public class ReceptionModel implements ObservableReceptionModel {

    private static final Logger LOGGER = Logger.getLogger(ReceptionModel.class.getName());

    private List<ReceptionModelObserver> observers;

    private ReceptionService receptionService;
    private List<Order> servedOrders;
    private List<Order> billedOrders;

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
                        Thread.sleep(3000);
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

    private void notifyOrdersUpdated() {
        for (ReceptionModelObserver observer : observers) {
            observer.ordersReceivedFromServer(this.servedOrders, this.billedOrders);
        }
    }

    private void setOrders(List<Order> orders) {
        servedOrders.clear();
        billedOrders.clear();

        orders.stream().forEach(order -> {
            switch (order.getState()) {
                case SERVED:
                    servedOrders.add(order);
                    break;
                case BILLED:
                    billedOrders.add(order);
                    break;
            }
        });

        notifyOrdersUpdated();
    }

    public void updateSelectedOrder(Order order) {
        try {
            receptionService.billOrder(order.getId());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Failed to send update command to RMI Server!", e);
        }
    }
    
    @Override
    public void addReceptionModelObserver(ReceptionModelObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeReceptionModelObserver(ReceptionModelObserver observer) {
        this.observers.remove(observer);
    }
}
