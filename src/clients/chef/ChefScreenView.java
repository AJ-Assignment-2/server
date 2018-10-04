/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 *
 * @author Imanuel
 */
public class ChefScreenView extends JFrame{
    private Border border;
    
    private JPanel centerPanel;
    private JPanel orderStatusPanel;
    private JPanel waitingOrdersPanel;
    private JPanel servingOrdersPanel;
    private JLabel waitingOrders;
    private JTextArea waitingOrdersTextArea;
    private JScrollPane scrollableWaitingOrdersTextArea;
    private JLabel servingOrders;
    private JTextArea servingOrdersTextArea;
    private JScrollPane scrollableServingOrdersTextArea;
    
    
    private JPanel southPanel;
    private JButton prepareButton;
    
    public ChefScreenView(){
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
        waitingOrdersTextArea=new JTextArea(10,50);
        waitingOrdersTextArea.setEditable(false);
        servingOrders=new JLabel("Orders with served state (No orders available to serve)");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        servingOrdersTextArea=new JTextArea(10,50);
        servingOrdersTextArea.setEditable(false);
        scrollableWaitingOrdersTextArea=new JScrollPane(waitingOrdersTextArea);
        scrollableWaitingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        scrollableServingOrdersTextArea=new JScrollPane(servingOrdersTextArea);
        scrollableServingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder("Order Status"));
        waitingOrdersPanel.add(waitingOrders, BorderLayout.NORTH);
        waitingOrdersPanel.add(scrollableWaitingOrdersTextArea, BorderLayout.SOUTH);
        servingOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        servingOrdersPanel.add(scrollableServingOrdersTextArea, BorderLayout.SOUTH);
        orderStatusPanel.add(waitingOrdersPanel);
        orderStatusPanel.add(servingOrdersPanel);
        centerPanel.add(orderStatusPanel);
        
        
        southPanel=new JPanel();
        prepareButton=new JButton("Prepare");
        southPanel.add(prepareButton);
        
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    public void addPrepareButtonListener(ActionListener prepareButtonListener){
        prepareButton.addActionListener(prepareButtonListener);
    }
}
