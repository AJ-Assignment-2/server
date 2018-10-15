package model.MenuItem;

import java.util.Comparator;

/**
 * Used to sort two different Lists containing menu items.
 * Two menu items are considered equal if they have the same name.
 *
 */
public class MenuItemComparator implements Comparator<MenuItem> {
    @Override
    public int compare(MenuItem o1, MenuItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
