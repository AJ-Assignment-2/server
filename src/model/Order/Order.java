package model.Order;

import model.MenuItem.MenuItem;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a customer's order with menu items.
 * Uses a HashMap to track the quantities of ordered MenuItems.
 */
public class Order implements Serializable {
    private int id;
    private String customerName;
    private HashMap<MenuItem, Integer> menuItemSelections;
    private OrderState state;
    private int tableNumber;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<MenuItem, Integer> getMenuItemSelections() {
        return menuItemSelections;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(String customerName, HashMap<MenuItem, Integer> menuItemSelections) {
        this.customerName = customerName;
        this.menuItemSelections = new HashMap<>(menuItemSelections);
    }

    /**
     * Add a menu item to the HashMap of selections.
     * If the item is already added, increment the HashMap key.
     *
     * @param item The item to add to the HashMap.
     */
    public void addItem(MenuItem item) {
        if (menuItemSelections.containsKey(item)) {
            menuItemSelections.put(item, menuItemSelections.get(item) + 1);
        } else {
            menuItemSelections.put(item, 1);
        }
    }

    public Order() {
        menuItemSelections = new HashMap<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            Order orderToCompare = (Order)obj;
            if(orderToCompare.getTableNumber() == this.getTableNumber()) {
                if (orderToCompare.getCustomerName().equals(this.getCustomerName())) {
                    if (orderToCompare.getId() == this.getId()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
