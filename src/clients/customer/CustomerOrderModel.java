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

/**
 * This model maintains the state of the customer client application.
 * This model queries the server once when it is initialised to retrieve all menu items from the RMI server.
 * It is also able to send the order to the RMI server for storage in the database.
 */
public class CustomerOrderModel {
    private static final Logger LOGGER = Logger.getLogger(CustomerOrderModel.class.getName());

    private List<MenuItem> menuItems;
    private Order customerOrder;
    private CustomerService customerService;

    /**
     * Initialise the model and get the menu items from the RMI server.
     */
    public CustomerOrderModel(){
        customerOrder = new Order();
        try {
            customerService = (CustomerService) Naming.lookup("rmi://127.0.0.1/Customer");
            menuItems = customerService.getMenuItems();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot connect to customer RMI service", e);
        }
    }

    /**
     * Retrieve all menu items with a particular type and category.
     * @param category category to search with
     * @param type type to search with
     * @return the filtered list of menu items.
     */
    public List<MenuItem> getMenuWithTypeCategory(MenuItemCategory category, MenuItemType type) {
        List<MenuItem> foodsWithTypeCategory = new ArrayList<>();
        
        menuItems.forEach(item -> {
            if (item.getCategory() == category && item.getType() == type) foodsWithTypeCategory.add(item);
        });
        
        return foodsWithTypeCategory;
    }

    /**
     * Add a menu item to the current.
     * @param itemToAdd the item to add to the current order.
     */
    public void addOrderItem(MenuItem itemToAdd) {
        customerOrder.addItem(itemToAdd);
    }

    /**
     * Get the current customer order.
     * @return
     */
    public Order getCustomerOrder() {
        return customerOrder;
    }

    /**
     * Set the current customer order
     * @param customerOrder customer order to set to
     */
    public void setCustomerOrder(Order customerOrder) {
        this.customerOrder = customerOrder;
    }

    /**
     * Submit the current order to the RMI server for storage in the database.
     */
    public void submitCustomerOrder() {
        try {
            customerOrder.setState(OrderState.WAITING);
            customerService.createOrder(customerOrder);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Unable to submit customer order", e);
        }
    }
}
