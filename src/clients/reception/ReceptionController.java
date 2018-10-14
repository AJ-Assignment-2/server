/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemComparator;
import model.MenuItem.MenuItemTableModel;
import model.MenuItem.MenuItemTotalsTableModel;
import model.Order.Order;
import model.Order.OrderComparator;
import model.Order.OrderState;
import model.Order.OrderTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Imanuel
 */
public class ReceptionController implements ReceptionModelObserver {
    private ReceptionView receptionView;
    private ReceptionModel receptionModel;

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

    private class MenuAboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            receptionView.showMessageDialog("", "About Us");
        }
    }

    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

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
                //table.setRowSelectionInterval(table.getSelectedRow(), table.getSelectedRow());

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
            }


        }
    }

    private class BillSelectedOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable servedOrdersTable = receptionView.getServedOrdersTable();
            OrderTableModel tableModel = (OrderTableModel) servedOrdersTable.getModel();
            Order selectedOrder = tableModel.getOrder(servedOrdersTable.getSelectedRow());
            if (selectedOrder != null) {
                receptionModel.updateSelectedOrder(selectedOrder);
            } else {
                receptionView.showErrorDialog("You have not selected an order!", "Order Not Selected");
            }
        }
    }
}
