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

/**
 *
 * @author Imanuel
 */
public class ReceptionModel implements ObservableReceptionModel {

    private static final Logger LOGGER = Logger.getLogger(ReceptionModel.class.getName());

    private List<ReceptionModelObserver> observers;

    private ReceptionService receptionService;
    private List<Order> servedOrders;

    public ReceptionModel() {
        observers = new ArrayList<>();
        try {
            receptionService = (ReceptionService) Naming.lookup("rmi://127.0.0.1/Reception");

            new Thread(() -> {
                try {
                    servedOrders = receptionService.getPreparedOrders();
                    notifyOrdersUpdated();
                    Thread.sleep(500);
                } catch (RemoteException e) {
                    LOGGER.log(Level.WARNING, "Attempt to retrieve orders through RMI failed", e);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Error sleeping thread", e);
                }
            }).start();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to reception RMI service", e);
        }
    }

    private void notifyOrdersUpdated() {
        for (ReceptionModelObserver observer : observers) {
            observer.ordersReceivedFromServer(servedOrders);
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
