/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import model.Order.Order;
import model.Order.OrderTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Imanuel
 */
public class ChefController implements ChefModelObserver{
    private ChefView chefView;
    private ChefModel chefModel;
    
    public ChefController(ChefView chefView, ChefModel chefModel){
        this.chefView = chefView;
        this.chefModel = chefModel;
        this.chefModel.addChefModelObserver(this);
        
        this.chefView.addPrepareButtonListener(new PrepareButtonListener());
    }
    
    class PrepareButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }

    @Override
    public void ordersUpdated(List<Order> waitingOrders, List<Order> servedOrders) {
        JTable waitingOrdersTable = chefView.getWaitingOrdersTable();
        OrderTableModel waitingOrdersTableModel = (OrderTableModel)waitingOrdersTable.getModel();

        JTable servedOrdersTable = chefView.getServedOrdersTable();
        OrderTableModel servedOrdersTableModel = (OrderTableModel)servedOrdersTable.getModel();

        waitingOrdersTableModel.setOrders(waitingOrders);
        servedOrdersTableModel.setOrders(servedOrders);

        waitingOrdersTableModel.fireTableDataChanged();
        servedOrdersTableModel.fireTableDataChanged();
    }


}
