package clients.reception;

import model.ColumnWidthUtil;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemComparator;
import model.MenuItem.MenuItemTotalsTableModel;
import model.Order.Order;
import model.Order.OrderTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The controller for the reception screen...
 * This controller observes the model so it can react to events and be notified of changes.
 */
public class ReceptionController implements ReceptionModelObserver {
    private ReceptionView receptionView;
    private ReceptionModel receptionModel;
    private BillView billView;

    /**
     * Register listeners and intialise references to the view and model.
     * @param receptionView The reception view
     * @param receptionModel The reception model
     */
    public ReceptionController(ReceptionView receptionView, ReceptionModel receptionModel) {
        this.receptionView = receptionView;
        this.receptionModel = receptionModel;
        this.receptionModel.addReceptionModelObserver(this);

        this.receptionView.addMenuAboutListener(new MenuAboutListener());
        this.receptionView.addBillButtonListener(new BillSelectedOrderListener());
        this.receptionView.addExitButtonListener(new ExitButtonListener());
        this.receptionView.getServedOrdersTable().getSelectionModel().addListSelectionListener(new OrderSelectedListener(receptionView.getServedOrdersTable()));
        this.receptionView.getBilledOrdersTable().getSelectionModel().addListSelectionListener(new OrderSelectedListener(receptionView.getBilledOrdersTable()));
    }

    /**
     * Be notified by the model that orders have been received from the server.
     * Use the new order data to populate the view's order JTables.
     * @param servedOrders new served orders
     * @param billedOrders new billed orders
     */
    @Override
    public void ordersReceivedFromServer(List<Order> servedOrders, List<Order> billedOrders) {
        JTable servedOrdersTable = receptionView.getServedOrdersTable();
        JTable billedOrdersTable = receptionView.getBilledOrdersTable();

        OrderTableModel servedOrdersTableModel = (OrderTableModel) servedOrdersTable.getModel();
        OrderTableModel billedOrdersTableModel = (OrderTableModel) billedOrdersTable.getModel();

        int servedOrdersSelectedRow = servedOrdersTable.getSelectedRow();
        int billedOrdersSelectedRow = billedOrdersTable.getSelectedRow();

        Order selectedServedOrder = null;
        Order selectedBilledOrder = null;

        if (servedOrdersSelectedRow != -1) {
            selectedServedOrder = servedOrdersTableModel.getOrder(servedOrdersSelectedRow);
        } else if (billedOrdersSelectedRow != -1) {
            selectedBilledOrder = billedOrdersTableModel.getOrder(billedOrdersSelectedRow);
        }

        servedOrdersTableModel.setOrders(servedOrders);
        billedOrdersTableModel.setOrders(billedOrders);

        servedOrdersTableModel.fireTableDataChanged();
        billedOrdersTableModel.fireTableDataChanged();

        if (selectedServedOrder != null) {
            for (Order order : servedOrders) {
                if (order.equals(selectedServedOrder)) {
                    int selectedRow = servedOrders.indexOf(order);
                    servedOrdersTable.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        } else if (selectedBilledOrder != null) {
            for (Order order : billedOrders) {
                if (order.equals(selectedBilledOrder)) {
                    int selectedRow = billedOrders.indexOf(order);
                    billedOrdersTable.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        }
    }

    /**
     * Display the About Us dialog if the menu button is clicked.
     */
    private class MenuAboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            receptionView.showMessageDialog("This simulator will simulate reception as client for an Assigment 2-Advance Java", "About Us");
        }
    }

    /**
     * Exit the application if there is a click on the exit button.
     */
    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

    /**
     * Display an orders menu items in the bottom JTable when an order is selected.
     */
    private class OrderSelectedListener implements ListSelectionListener {
        private final JTable table;

        public OrderSelectedListener(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (table.getSelectedRow() != -1) {
                OrderTableModel tableModel = (OrderTableModel) table.getModel();
                Order selectedOrder = tableModel.getOrder(table.getSelectedRow());

                List<MenuItem> menuItems = new ArrayList<>();

                for (MenuItem item : selectedOrder.getMenuItemSelections().keySet()) {
                    int quantity = selectedOrder.getMenuItemSelections().get(item);

                    for (int i = 0; i < quantity; i++) {
                        menuItems.add(item);
                    }
                }
                
                Collections.sort(menuItems, new MenuItemComparator());

                MenuItemTotalsTableModel menuItemTableModel = (MenuItemTotalsTableModel) receptionView.getOrderItemDetailTable().getModel();
                menuItemTableModel.setMenuItems(menuItems);
                menuItemTableModel.fireTableDataChanged();
                ColumnWidthUtil.adjustColumnWidths(receptionView.getOrderItemDetailTable(), new int[]{0});
            }
        }
    }

    /**
     * Tell the model to update the state of the selected order when the Bill button is pressed.
     */
    private class BillSelectedOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable servedOrdersTable = receptionView.getServedOrdersTable();
            OrderTableModel tableModel = (OrderTableModel) servedOrdersTable.getModel();
            Order selectedOrder = tableModel.getOrder(servedOrdersTable.getSelectedRow());
            if (selectedOrder != null) {
                int confirmationResult = JOptionPane.showConfirmDialog(receptionView, "Are you sure you want to bill this order?", "Mark order as billed", JOptionPane.YES_NO_OPTION);

                if (confirmationResult == JOptionPane.YES_OPTION) {
                    receptionModel.updateSelectedOrder(selectedOrder);
                    BillView billView = new BillView(selectedOrder);
                    BillController billController = new BillController(billView);
                    billView.setTitle("Customer Receipt");
                    billView.setSize(1000,600);
                    billView.setVisible(true);
                }
            } else {
                receptionView.showErrorDialog("You have not selected an order!", "Order Not Selected");
            }
        }
    }
}
