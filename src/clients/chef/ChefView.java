/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Imanuel
 */
public class ChefView extends JFrame{
    private Border border;
    
    private JPanel centerPanel;
    private JPanel orderStatusPanel;
    private JPanel waitingOrdersPanel;
    private JPanel servingOrdersPanel;
    private JLabel waitingOrders;
    private JScrollPane waitingOrdersContainer;
    private JLabel servingOrders;
    private JScrollPane servedOrdersContainer;
    private JTable servedOrdersTable;
    private JTable waitingOrdersTable;
    private JTable orderDetailsTable;
    
    
    private JPanel southPanel;
    private JButton prepareButton;
    
    public ChefView(){
        border=BorderFactory.createLineBorder(Color.BLACK);
                
        centerPanel=new JPanel();
        orderStatusPanel=new JPanel();
        waitingOrdersPanel=new JPanel();
        waitingOrdersPanel.setLayout(new BorderLayout());
        servingOrdersPanel=new JPanel();
        servingOrdersPanel.setLayout(new BorderLayout());
        orderStatusPanel.setLayout(new GridLayout(1,2,50,10));
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder("Order Status"));
        waitingOrders=new JLabel("Orders with waiting state (No orders available to prepare)");
        waitingOrders.setHorizontalAlignment(JLabel.CENTER);
        servedOrdersTable = new JTable();
        waitingOrdersTable = new JTable();
        orderDetailsTable = new JTable();

        servingOrders=new JLabel("Orders with served state (No orders available to serve)");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        waitingOrdersContainer =new JScrollPane(waitingOrdersTable);
        waitingOrdersContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        waitingOrdersContainer.setPreferredSize(new Dimension(300,150));
        servedOrdersContainer =new JScrollPane(servedOrdersTable);
        servedOrdersContainer.setPreferredSize(new Dimension(300,150));
        servedOrdersContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder("Order Status"));
        waitingOrdersPanel.add(waitingOrders, BorderLayout.NORTH);
        waitingOrdersPanel.add(waitingOrdersContainer, BorderLayout.SOUTH);
        servingOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        servingOrdersPanel.add(servedOrdersContainer, BorderLayout.SOUTH);
        orderStatusPanel.add(waitingOrdersPanel);
        orderStatusPanel.add(servingOrdersPanel);
        centerPanel.add(orderStatusPanel);
        
        
        southPanel=new JPanel();
        prepareButton=new JButton("Prepare");
        southPanel.add(prepareButton);
        
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public JScrollPane getWaitingOrdersContainer() {
        return waitingOrdersContainer;
    }

    public JTable getOrderDetailsTable() {
        return orderDetailsTable;
    }

    public JTable getWaitingOrdersTable() {
        return waitingOrdersTable;
    }

    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    public void addPrepareButtonListener(ActionListener prepareButtonListener){
        prepareButton.addActionListener(prepareButtonListener);
    }
}
