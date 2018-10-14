package server.service.OrderService;

import model.MenuItem.MenuItem;
import model.Order.Order;
import server.service.MenuItemService.MenuItemAccessor;
import server.service.MenuItemService.MenuItemDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOrderModel {
    private MenuItemDao menuItemAccessor;
    private Order customerOrder;
    private List<MenuItem> menuItemList;

    public RestaurantOrderModel() {
        customerOrder = new Order();

        try {
            menuItemAccessor = new MenuItemAccessor(DriverManager.getConnection("jdbc:derby:RestaurantOrderingDB;create=true"));
        } catch (Exception e) {
            // do nothing
        }

        menuItemList = new ArrayList<>(menuItemAccessor.readMenuItems());

        // Adding menu item to an order
        addMenuItemToOrder(menuItemList.get(0));
    }

    private void addMenuItemToOrder(MenuItem item) {
        customerOrder.addItem(item);
    }
}
