package clients.customer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import model.Order.OrderState;
import server.rmi.customer.CustomerService;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Imanuel
 */
public class CustomerOrderModel implements ObservableCustomerOrderModel{
    private static final Logger LOGGER = Logger.getLogger(CustomerOrderModel.class.getName());

    private List<MenuItem> menuItems;
    private Order customerOrder;
    private CustomerService customerService;
    private List<CustomerOrderModelObserver> observers;
    
    
    public CustomerOrderModel(){
        observers = new ArrayList<>();
        customerOrder = new Order();
        try {
            customerService = (CustomerService) Naming.lookup("rmi://127.0.0.1/Customer");
            menuItems = customerService.getMenuItems();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to customer RMI service", e);
        }
    }

    public List<MenuItem> getMenuWithTypeCategory(MenuItemCategory category, MenuItemType type) {
        List<MenuItem> foodsWithTypeCategory = new ArrayList<>();
        
        menuItems.forEach(item -> {
            if (item.getCategory() == category && item.getType() == type) foodsWithTypeCategory.add(item);
        });
        
        return foodsWithTypeCategory;
    }

    public void addOrderItem(MenuItem itemToAdd) {
        customerOrder.addItem(itemToAdd);
    }

    public Order getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(Order customerOrder) {
        this.customerOrder = customerOrder;
    }

    public void submitCustomerOrder() {
        try {
            customerOrder.setState(OrderState.WAITING);
            customerService.createOrder(customerOrder);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Unable to submit customer order", e);
        }
    }

    @Override
    public void addReceptionModelObserver(CustomerOrderModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeReceptionModelObserver(CustomerOrderModelObserver observer) {
        observers.remove(observer);
    }
}
