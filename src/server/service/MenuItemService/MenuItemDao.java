package server.service.MenuItemService;

import model.MenuItem.MenuItem;

import java.util.List;

public interface MenuItemDao {
    List<MenuItem> readMenuItems();
    void createMenuItem(MenuItem itemToAdd);
}
