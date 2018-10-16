/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.MenuItem.BillItem;
import model.MenuItem.BillTableModel;
import model.MenuItem.MenuItem;
import model.Order.Order;

/**
 *
 * @author Imanuel
 */
public class BillView extends JFrame {

    private JPanel northPanel;
    private JLabel billTitle;

    
    private JPanel centerPanel;
    private JPanel customerDetailsPanel;
    private List<BillItem> billItems;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JLabel customerNameLabel;
    private JLabel tableNumberLabel;
    private JTable orderListTable;
    private JScrollPane orderListPanel;
    
    
    private JPanel southPanel;
    private JButton printBillButton;

    public BillView(Order selectedOrder) {
        northPanel = new JPanel();
        billTitle = new JLabel("BILL RECEIPT");
        northPanel.add(billTitle);

        
        centerPanel = new JPanel(new BorderLayout());
        customerDetailsPanel = new JPanel(new GridLayout(4,1));
        customerNameLabel = new JLabel("Customer name: " + selectedOrder.getCustomerName());
        tableNumberLabel = new JLabel("Table Number: " + selectedOrder.getTableNumber());
        dateLabel = new JLabel("Date: "+LocalDate.now());
        timeLabel = new JLabel("Time: "+LocalTime.now());
        customerDetailsPanel.add(customerNameLabel);
        customerDetailsPanel.add(tableNumberLabel);
        customerDetailsPanel.add(dateLabel);
        customerDetailsPanel.add(timeLabel);

        
        orderListTable = new JTable(new BillTableModel(getListOrdered(selectedOrder)));
        orderListPanel = new JScrollPane(orderListTable);
        centerPanel.add(customerDetailsPanel, BorderLayout.NORTH);
        centerPanel.add(orderListPanel, BorderLayout.SOUTH);
        

        southPanel = new JPanel();
        printBillButton = new JButton("Print Bill");
        southPanel.add(printBillButton);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void addPrintBillButtonListener(ActionListener printBillButtonListener) {
        printBillButton.addActionListener(printBillButtonListener);
    }

    public void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public JTable getCustomerOrderList() {
        return orderListTable;
    }
    
    public List<BillItem> getListOrdered(Order selectedOrder) {
        int count = 1;
        BillItem billItem;
        billItems = new ArrayList<>();
        System.out.println(selectedOrder.getCustomerName());
        System.out.println(selectedOrder.getTableNumber());
        System.out.println("SIZE: "+selectedOrder.getMenuItemSelections().keySet().size());
        for(MenuItem item:selectedOrder.getMenuItemSelections().keySet()) {
            float total = selectedOrder.getMenuItemSelections().get(item)*item.getPrice();
            billItem = new BillItem(selectedOrder.getMenuItemSelections().get(item), item.getName(), item.getPrice(), total);
            billItems.add(billItem);
        }
        return billItems;
    }
}
