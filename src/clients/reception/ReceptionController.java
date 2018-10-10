/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemTableModel;
import model.Order.Order;
import model.Order.OrderState;
import model.Order.OrderTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Imanuel
 */
public class ReceptionController implements ReceptionModelObserver {
    private ReceptionView receptionView;
    private ReceptionModel receptionModel;
    
    public ReceptionController(ReceptionView receptionView, ReceptionModel receptionModel){
        this.receptionView = receptionView;
        this.receptionModel = receptionModel;
        this.receptionModel.addReceptionModelObserver(this);
        
        this.receptionView.addMenuAboutListener(new MenuAboutListener());
        this.receptionView.addBillButtonListener(new BillSelectedOrderListener());
        this.receptionView.addExitButtonListener(new ExitButtonListener());
        this.receptionView.getServedOrdersTable().getSelectionModel().addListSelectionListener(new OrderSelectedListener());
    }

    @Override
    public void ordersReceivedFromServer(List<Order> servedOrders, List<Order> billedOrders) {
        this.receptionView.getServedOrdersTable().setModel(new OrderTableModel(servedOrders));
        this.receptionView.getBilledOrdersTable().setModel(new OrderTableModel(billedOrders));
    }
    
    private class MenuAboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            receptionView.showMessageDialog("","About Us");
        }
    }
    
    private class ExitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }

    private class OrderSelectedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JTable servedOrdersTable = receptionView.getServedOrdersTable();
            int selectedRow = servedOrdersTable.getSelectedRow();
            if (selectedRow >= 0) {
                OrderTableModel tableModel = (OrderTableModel) servedOrdersTable.getModel();
                Order selectedOrder = tableModel.getOrder(selectedRow);

                List<MenuItem> menuItems = new ArrayList<>(selectedOrder.getMenuItemSelections().keySet());

                receptionView.getOrderItemDetailTable().setModel(new MenuItemTableModel(menuItems));
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
