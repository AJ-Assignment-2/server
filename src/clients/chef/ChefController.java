package clients.chef;

import model.ColumnWidthUtil;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemComparator;
import model.MenuItem.MenuItemTableModel;
import model.Order.Order;
import model.Order.OrderTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The controller for the chef screen.
 * This controller holds references to both the view and the model and can respond
 * to notifications from the model.
 */
public class ChefController implements ChefModelObserver {
    private ChefView chefView;
    private ChefModel chefModel;

    /**
     * Initialises references to view and model.
     * Registers the view's buttons with listeners declared in the controller.
     *
     * @param chefView a reference to the chef view
     * @param chefModel a reference to the chef model
     */
    public ChefController(ChefView chefView, ChefModel chefModel) {
        this.chefView = chefView;
        this.chefModel = chefModel;
        // Register as a model observer
        this.chefModel.addChefModelObserver(this);

        this.chefView.addMenuAboutListener(new MenuAboutListener());
        
        this.chefView.addPrepareButtonListener(new PrepareButtonListener());
        this.chefView.addWaitingOrdersRowSelectedListener(new OrderTableRowSelectionListener(chefView.getWaitingOrdersTable()));
        this.chefView.addServedOrdersRowSelectedListener(new OrderTableRowSelectionListener(chefView.getServedOrdersTable()));
    }

    /**
     * Display the About Us dialog if the menu button is clicked.
     */
    private class MenuAboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chefView.showMessageDialog("Advanced Java - Assignment 2\nChef Client Application", "About Us");
        }
    }
    
    
    /**
     * Be notified that the model contains a new list of orders.
     * Additional logic exits in this method to retain the selected row on table data update.
     *
     * @param waitingOrders new list of waiting orders.
     * @param servedOrders new list of served orders
     */
    @Override
    public void ordersUpdated(List<Order> waitingOrders, List<Order> servedOrders) {
        JTable waitingOrdersTable = chefView.getWaitingOrdersTable();
        OrderTableModel waitingOrdersTableModel = (OrderTableModel) waitingOrdersTable.getModel();

        JTable servedOrdersTable = chefView.getServedOrdersTable();
        OrderTableModel servedOrdersTableModel = (OrderTableModel) servedOrdersTable.getModel();

        Order selectedWaitingOrder = null;
        Order selectedServedOrder = null;
        int waitingOrdersTableSelectedRow = waitingOrdersTable.getSelectedRow();
        int servedOrdersTableSelectedRow = servedOrdersTable.getSelectedRow();

        if (waitingOrdersTableSelectedRow != -1) {
            selectedWaitingOrder = waitingOrdersTableModel.getOrder(waitingOrdersTableSelectedRow);
        } else if (servedOrdersTableSelectedRow != -1) {
            selectedServedOrder = servedOrdersTableModel.getOrder(servedOrdersTableSelectedRow);
        }

        waitingOrdersTableModel.setOrders(waitingOrders);
        servedOrdersTableModel.setOrders(servedOrders);

        waitingOrdersTableModel.fireTableDataChanged();
        servedOrdersTableModel.fireTableDataChanged();

        // Check if the selected order is still in the table, if it is, select it.
        if (selectedWaitingOrder != null) {
            for (Order order : waitingOrders) {
                if (order.equals(selectedWaitingOrder)) {
                    int selectedRow = waitingOrders.indexOf(order);
                    waitingOrdersTable.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        } else if (selectedServedOrder != null) {
            for (Order order : servedOrders) {
                if (order.equals(selectedServedOrder)) {
                    int selectedRow = servedOrders.indexOf(order);
                    servedOrdersTable.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        }

    }

    /**
     * When the prepare button is pressed, this action listener sends the order to
     * the model so the server can be updated of the order's change of state.
     *
     * The prepare button is disabled if a order is not selected.
     */
    private class PrepareButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable waitingOrdersTable = chefView.getWaitingOrdersTable();
            OrderTableModel tableModel = (OrderTableModel) waitingOrdersTable.getModel();
            Order selectedOrder = tableModel.getOrder(waitingOrdersTable.getSelectedRow());

            if (selectedOrder != null) {
                int confirmationResult = JOptionPane.showConfirmDialog(chefView, "Are you sure you want to mark this order as served?", "Mark as served", JOptionPane.YES_NO_OPTION);

                if (confirmationResult == JOptionPane.YES_OPTION) {
                    chefModel.updateSelectedOrder(selectedOrder);
                }

            } else {
                JOptionPane.showMessageDialog(chefView, "You don't have an order selected!");
            }
        }
    }

    /**
     * When a table row is selected from one of the order tables, display the order's
     * menu items in the menu item JTable.
     */
    private class OrderTableRowSelectionListener implements ListSelectionListener {
        private final JTable table;

        public OrderTableRowSelectionListener(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (table.getSelectedRow() != -1) {
                OrderTableModel tableModel = (OrderTableModel) table.getModel();
                Order selectedOrder = tableModel.getOrder(table.getSelectedRow());

                List<MenuItem> selectedOrderMenuItems = new ArrayList<>();

                for (MenuItem item : selectedOrder.getMenuItemSelections().keySet()) {
                    int quantity = selectedOrder.getMenuItemSelections().get(item);

                    for (int i = 0; i < quantity; i++) {
                        selectedOrderMenuItems.add(item);
                    }
                }

                Collections.sort(selectedOrderMenuItems, new MenuItemComparator());
                MenuItemTableModel menuItemTableModel = (MenuItemTableModel) chefView.getOrderDetailsTable().getModel();
                menuItemTableModel.setMenuItems(selectedOrderMenuItems);
                menuItemTableModel.fireTableDataChanged();
                ColumnWidthUtil.adjustColumnWidths(chefView.getOrderDetailsTable(), new int[]{0});
            }
        }
    }


}
