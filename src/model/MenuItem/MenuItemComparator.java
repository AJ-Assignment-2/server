package model.MenuItem;

import java.util.Comparator;

public class MenuItemComparator implements Comparator<MenuItem> {
    @Override
    public int compare(MenuItem o1, MenuItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
