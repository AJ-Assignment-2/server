package server.service.MenuItemService;

import model.MenuItem.MenuItem;

import java.util.List;

/**
 * The contract specifying what operations may be performed on MenuItems in the Derby database.
 */
public interface MenuItemDao {
    /**
     * Returns all menu items in the Derby database.
     * @return a List of all menu items in the Derby database.
     */
    List<MenuItem> readMenuItems();

    /**
     * Creates a given menu item in the derby database.
     * @param itemToAdd The menu item to add
     */
    void createMenuItem(MenuItem itemToAdd);
}
