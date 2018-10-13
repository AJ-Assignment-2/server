/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

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
import java.util.List;

/**
 * @author Imanuel
 */
public class ChefController implements ChefModelObserver {
    private ChefView chefView;
    private ChefModel chefModel;

    public ChefController(ChefView chefView, ChefModel chefModel) {
        this.chefView = chefView;
        this.chefModel = chefModel;
        this.chefModel.addChefModelObserver(this);

        this.chefView.addPrepareButtonListener(new PrepareButtonListener());
        this.chefView.addWaitingOrdersRowSelectedListener(new OrderTableRowSelectionListener(chefView.getWaitingOrdersTable()));
        this.chefView.addServedOrdersRowSelectedListener(new OrderTableRowSelectionListener(chefView.getServedOrdersTable()));
    }

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

//        TableRowSorter<OrderTableModel> sorter = new TableRowSorter<>((OrderTableModel)servedOrdersTable.getModel());
//        List <RowSorter.SortKey> sortKeys = new ArrayList<>();
//        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//        sorter.setSortKeys(sortKeys);
//        waitingOrdersTable.setRowSorter(sorter);
//        servedOrdersTable.setRowSorter(sorter);

        waitingOrdersTableModel.fireTableDataChanged();
        servedOrdersTableModel.fireTableDataChanged();

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

    private class PrepareButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable waitingOrdersTable = chefView.getWaitingOrdersTable();
            OrderTableModel tableModel = (OrderTableModel) waitingOrdersTable.getModel();
            Order selectedOrder = tableModel.getOrder(waitingOrdersTable.getSelectedRow());

            if (selectedOrder != null) {
                chefModel.updateSelectedOrder(selectedOrder);
            } else {
                //chefView.showErrorDialog("You have not selected an order!", "Order Not Selected");
            }
        }
    }

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

                MenuItemTableModel menuItemTableModel = (MenuItemTableModel) chefView.getOrderDetailsTable().getModel();
                menuItemTableModel.setMenuItems(new ArrayList<>(selectedOrder.getMenuItemSelections().keySet()));
                menuItemTableModel.fireTableDataChanged();
            }
        }
    }
}
