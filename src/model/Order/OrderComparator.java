package model.Order;

import java.util.Comparator;

/**
 * Used to sort a list of orders.
 * Orders are considered equal if their customer names are the same.
 */
public class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getCustomerName().compareTo(o2.getCustomerName());
    }

}
