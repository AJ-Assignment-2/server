/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import model.Order.Order;
import server.rmi.chef.ChefService;

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
public class ChefModel implements ObservableChefModel{
    private Logger LOGGER = Logger.getLogger(ChefModel.class.getName());

    private ChefService chefService;
    private List<ChefModelObserver> observers;
    private List<Order> waitingOrders;

    public ChefModel(){
        observers = new ArrayList<>();

        try {
            chefService = (ChefService) Naming.lookup("rmi://127.0.0.1/Chef");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to customer RMI service", e);
        }

        // Retrieve all waiting orders from the database and notify the controller each time
        new Thread(() -> {

            while (true) {
                try {
                    // TODO: Optimise so list is only updated and notification is only fired if received list is different.
                    waitingOrders = chefService.getWaitingOrders();
                    notifyWaitingOrdersUpdated();
                    Thread.sleep(500);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Attempt to retrieve orders through RMI failed", e);
                }
            }
        }).start();
    }

    private void notifyWaitingOrdersUpdated() {
        for (ChefModelObserver observer : observers) {
            observer.waitingOrdersUpdated(waitingOrders);
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
