/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 *
 * @author Imanuel
 */
public class ReceptionView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;
    
    private Border border;
    
    private JPanel northPanel;
    private JPanel customerDetailsPanel;
    private JPanel customerNamePanel;
    private JPanel customerTableNumberPanel;
    private JLabel customerNameLabel;
    private JTextArea customerNameTextArea;
    private JLabel customerTableNumber;
    private JComboBox selectedTableNumber;
      
    
    private JPanel centerPanel;
    private JPanel servingOrdersPanel;
    private JPanel orderStatusPanel;
    private JLabel servingOrders;
    private JTextArea servingOrdersTextArea;
    private JScrollPane scrollableServingOrdersTextArea;
    
    private JPanel southPanel;
    private JButton addCustomerButton;
    private JButton billButton;
    private JButton exitButton;
    
    private String[][] labels={{"Customer Details"}, {"Order Status"}, {"Command Buttons"}};
    
    public ReceptionView(){
        this.menuBar=new JMenuBar();
        this.menuFile=new JMenu("File");
        this.menuAbout=new JMenuItem("About");
        menuFile.add(menuAbout);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);
        
        border=BorderFactory.createLineBorder(Color.BLACK);
        
        northPanel=new JPanel();
        customerDetailsPanel=new JPanel(new GridLayout(1,2));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder(labels[0][0]));
        customerNamePanel=new JPanel();
        customerTableNumberPanel=new JPanel();
        customerNameLabel=new JLabel("Customer Name ");
        customerNameTextArea=new JTextArea(1,8);
        customerNameTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        customerTableNumber=new JLabel("Table Number");
        selectedTableNumber=new JComboBox<>();
        setupComboBox();
        selectedTableNumber.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        customerNamePanel.add(customerNameLabel);
        customerNamePanel.add(customerNameTextArea);
        customerTableNumberPanel.add(customerTableNumber);
        customerTableNumberPanel.add(selectedTableNumber);
        customerDetailsPanel.add(customerNamePanel);
        customerDetailsPanel.add(customerTableNumberPanel);
        northPanel.add(customerDetailsPanel);

        centerPanel=new JPanel(new BorderLayout());
        servingOrdersPanel=new JPanel();
        servingOrdersPanel.setLayout(new BorderLayout());
        orderStatusPanel=new JPanel();
        orderStatusPanel.setLayout(new GridLayout(1,1,50,10));
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder(labels[1][0]));
        servingOrders=new JLabel("Orders with served state (No orders available to serve)");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        servingOrdersTextArea=new JTextArea(10,20);
        servingOrdersTextArea.setEditable(false);
        scrollableServingOrdersTextArea=new JScrollPane(servingOrdersTextArea);
        scrollableServingOrdersTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        servingOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        servingOrdersPanel.add(scrollableServingOrdersTextArea, BorderLayout.SOUTH);
        orderStatusPanel.add(servingOrdersPanel);
        centerPanel.add(orderStatusPanel);
        
        
        southPanel=new JPanel();
        addCustomerButton=new JButton("Add Customer");
        billButton=new JButton("Bill");
        exitButton=new JButton("Exit");
        southPanel.add(addCustomerButton);
        southPanel.add(billButton);
        southPanel.add(exitButton);
        
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    public void setupComboBox(){
        selectedTableNumber.addItem("Select table number");
        for(int i=1;i<9;i++){
            selectedTableNumber.addItem(i);
        }
    }
    
    public void addCustomerButtonListener(ActionListener addCustomerButtonListener){
        addCustomerButton.addActionListener(addCustomerButtonListener);
    }
    
    public void addBillButtonListener(ActionListener billButtonListener){
        billButton.addActionListener(billButtonListener);
    }
    
    public void addExitButtonListener(ActionListener exitButtonListener){
        exitButton.addActionListener(exitButtonListener);
    }
    
    public void addMenuAboutListener(ActionListener menuAboutListener){
        menuAbout.addActionListener(menuAboutListener);
    }
    
    public void showMessageDialog(String information, String titleDialog){
        JOptionPane.showMessageDialog(this,information,titleDialog,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showErrorDialog(String information, String titleDialog){
        JOptionPane.showMessageDialog(this,information,titleDialog,JOptionPane.ERROR_MESSAGE);
    }
    
    public String getCustomerName(){
        return customerNameTextArea.getText();
    }
    
    public String getTableNumber(){
        return String.valueOf(selectedTableNumber.getSelectedItem());
    }
}
